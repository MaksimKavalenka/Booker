package by.training.solr.uploader.impl;

import static by.training.constants.DefaultConstants.DEFAULT_SYMBOLS_COUNT;
import static by.training.constants.DelimiterConstants.SPACE_DELIMITER;
import static by.training.constants.SolrConstants.Core.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import by.training.constants.SolrConstants.Fields.ContentFields;
import by.training.constants.SolrConstants.Fields.MetadataFields;
import by.training.exception.UploadException;
import by.training.exception.ValidationException;
import by.training.parser.AdvancedEpubParser;
import by.training.parser.Parser;
import by.training.solr.uploader.SolrUploadable;
import by.training.utility.Utility;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.epub.EpubReader;

@Service("epubSolrUploader")
public class EpubSolrUploader implements SolrUploadable {

    public EpubSolrUploader() {
    }

    @Override
    public void upload(String directoryPath, String fileName, String id, String uploader)
            throws UploadException {
        String filePath = directoryPath + "/" + fileName;

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            EpubReader epubReader = new EpubReader();
            Book book = epubReader.readEpub(in);

            try (InputStream bookInputStream = book.getCoverImage().getInputStream()) {
                Utility.uploadFile(book.getCoverImage().getInputStream(), directoryPath + "/" + id);
            }

            try (SolrClient contentClient = new HttpSolrClient(CONTENT_CORE_URI);
                    SolrClient metadataClient = new HttpSolrClient(METADATA_CORE_URI)) {
                long pagesCount = 0;

                {
                    Tika tika = new Tika();
                    org.apache.tika.parser.Parser parser = new AdvancedEpubParser();
                    Metadata metadata = new Metadata();
                    ContentHandler handler = new BodyContentHandler(-1);

                    try (BufferedInputStream bin = new BufferedInputStream(
                            new FileInputStream(filePath))) {
                        metadata.set(HttpHeaders.CONTENT_TYPE, tika.detect(in));
                        parser.parse(bin, handler, metadata, new ParseContext());

                        int index = 0;
                        while (index < handler.toString().length()) {
                            ++pagesCount;

                            SolrInputDocument inputDocument = new SolrInputDocument();

                            int lastIndex = index;
                            int size = handler.toString().length();
                            if ((index + DEFAULT_SYMBOLS_COUNT) < size) {
                                index = handler.toString().lastIndexOf(SPACE_DELIMITER,
                                        index + DEFAULT_SYMBOLS_COUNT);
                            } else {
                                index = size - 1;
                            }
                            inputDocument.setField(ContentFields.CONTENT,
                                    handler.toString().substring(lastIndex, index).trim());
                            ++index;

                            inputDocument.setField(ContentFields.ID, id + pagesCount);
                            inputDocument.setField(ContentFields.METADATA_ID, id);
                            inputDocument.setField(ContentFields.PAGE, pagesCount);

                            contentClient.add(inputDocument);
                        }
                    }
                }

                {
                    SolrInputDocument inputDocument = new SolrInputDocument();

                    inputDocument.setField(MetadataFields.DESCRIPTION,
                            book.getMetadata().getDescriptions());
                    inputDocument.setField(MetadataFields.FILE_NAME, fileName);
                    inputDocument.setField(MetadataFields.ID, id);
                    inputDocument.setField(MetadataFields.PAGES_COUNT, pagesCount);
                    inputDocument.setField(MetadataFields.PUBLISHER,
                            book.getMetadata().getPublishers());
                    inputDocument.setField(MetadataFields.TITLE, book.getTitle());
                    inputDocument.setField(MetadataFields.UPLOAD_DATE, new java.util.Date());
                    inputDocument.setField(MetadataFields.UPLOADER, uploader);

                    for (Identifier identifier : book.getMetadata().getIdentifiers()) {
                        if (Identifier.Scheme.ISBN.equals(identifier.getScheme())) {
                            inputDocument.addField(MetadataFields.ISBN, identifier.getValue());
                        }
                    }

                    for (Author author : book.getMetadata().getAuthors()) {
                        inputDocument.addField(MetadataFields.AUTHOR, Parser.parseAuthor(author));
                    }

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

                    metadataClient.add(inputDocument);
                }

                contentClient.commit(true, true);
                metadataClient.commit(true, true);
            }

        } catch (IOException | ParseException | SAXException | SolrServerException | TikaException
                | ValidationException e) {
            throw new UploadException(e.getMessage());
        }
    }

}
