package by.training.solr.uploader.impl;

import static by.training.constants.SolrConstants.Core.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.epub.EpubParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.OutOfPagesException;
import com.github.mertakdut.exception.ReadingException;

import by.training.constants.SolrConstants.Fields.ContentFields;
import by.training.constants.SolrConstants.Fields.MetadataFields;
import by.training.exception.UploadException;
import by.training.exception.ValidationException;
import by.training.solr.uploader.SolrUploadable;
import by.training.utility.Parser;
import by.training.utility.Utility;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.epub.EpubReader;

@Service("epubSolrUploader")
public class EpubSolrUploader implements SolrUploadable {

    private int progress;

    public EpubSolrUploader() {
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public void upload(String directoryPath, String fileName, String id, String uploader)
            throws UploadException {
        String filePath = directoryPath + "/" + fileName;

        try (InputStream in = new FileInputStream(filePath)) {
            EpubReader epubReader = new EpubReader();
            Book book = epubReader.readEpub(in);

            try (InputStream bookInputStream = book.getCoverImage().getInputStream()) {
                Utility.uploadFile(book.getCoverImage().getInputStream(), directoryPath + "/" + id);
            }

            uploadMetadata(book, id, fileName, uploader);
            uploadContent(id, filePath);
        } catch (IOException | ValidationException e) {
            throw new UploadException(e.getMessage());
        }
    }

    private void uploadMetadata(Book book, String id, String fileName, String uploader)
            throws UploadException {
        try (SolrClient client = new HttpSolrClient(METADATA_CORE_URI)) {
            SolrInputDocument inputDocument = new SolrInputDocument();

            inputDocument.setField(MetadataFields.DESCRIPTION,
                    book.getMetadata().getDescriptions());
            inputDocument.setField(MetadataFields.FILE_NAME, fileName);
            inputDocument.setField(MetadataFields.ID, id);
            inputDocument.setField(MetadataFields.PUBLISHER, book.getMetadata().getPublishers());
            inputDocument.setField(MetadataFields.TITLE, book.getTitle());
            inputDocument.setField(MetadataFields.UPLOAD_DATE, new java.util.Date());
            inputDocument.setField(MetadataFields.UPLOADER, uploader);

            List<Identifier> identifiers = book.getMetadata().getIdentifiers();
            List<String> strIdentifiers = new ArrayList<>(identifiers.size());
            for (Identifier identifier : book.getMetadata().getIdentifiers()) {
                if (Identifier.Scheme.ISBN.equals(identifier.getScheme())) {
                    strIdentifiers.add(identifier.getValue());
                }
            }
            inputDocument.setField(MetadataFields.ISBN, strIdentifiers);

            List<Author> authors = book.getMetadata().getAuthors();
            List<String> strAuthors = new ArrayList<>(authors.size());
            for (Author author : authors) {
                strAuthors.add(author.toString());
            }
            inputDocument.setField(MetadataFields.AUTHOR, strAuthors);

            for (Date date : book.getMetadata().getDates()) {
                Date.Event event = date.getEvent();

                if (event == null) {
                    event = Date.Event.CREATION;
                }

                switch (event) {
                    case CREATION:
                        inputDocument.setField(MetadataFields.CREATION_DATE,
                                Parser.parseToIso8601(date.getValue()));
                        break;
                    case PUBLICATION:
                        inputDocument.setField(MetadataFields.PUBLICATION_DATE,
                                Parser.parseToIso8601(date.getValue()));
                        break;
                    default:
                        break;
                }
            }

            client.add(inputDocument);
            client.commit(true, true);
        } catch (IOException | ParseException | SolrServerException e) {
            throw new UploadException(e.getMessage());
        }
    }

    private void uploadContent(String id, String filePath) throws UploadException {
        try (SolrClient client = new HttpSolrClient(CONTENT_CORE_URI)) {

            Tika tika = new Tika();
            org.apache.tika.parser.Parser parser = new EpubParser();
            Metadata metadata = new Metadata();
            ContentHandler handler = new BodyContentHandler(-1);

            try (BufferedInputStream inputStream = new BufferedInputStream(
                    new FileInputStream(filePath))) {
                metadata.set(HttpHeaders.CONTENT_TYPE, tika.detect(inputStream));

                parser.parse(inputStream, handler, metadata, new ParseContext());

                for (String name : metadata.names()) {
                    System.out.println(name + " : " + metadata.get(name));
                }

                System.out.println("File content: ");
                System.out.println(handler.toString());
            } catch (IOException | SAXException | TikaException e) {
                throw new UploadException(e.getMessage());
            }

            Reader reader = new Reader();
            reader.setFullContent(filePath);
            reader.setMaxContentPerSection(1000);
            reader.setIsIncludingTextContent(true);

            int size = reader.getToc().getNavMap().getNavPoints().size();
            for (int i = 0; i < size; i++) {
                SolrInputDocument inputDocument = new SolrInputDocument();

                String content = reader.readSection(i).getSectionTextContent();

                inputDocument.setField(ContentFields.ID, id + i + 1);
                inputDocument.setField(ContentFields.METADATA_ID, id);
                inputDocument.setField(ContentFields.CONTENT, content);

                client.add(inputDocument);

                progress = (int) Math.ceil((double) i * 100 / size);
            }

            client.commit(true, true);
        } catch (IOException | OutOfPagesException | ReadingException | SolrServerException e) {
            throw new UploadException(e.getMessage());
        }
    }

}
