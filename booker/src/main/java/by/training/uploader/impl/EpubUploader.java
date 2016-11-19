package by.training.uploader.impl;

import static by.training.constants.SolrConstants.Core.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.OutOfPagesException;
import com.github.mertakdut.exception.ReadingException;

import by.training.constants.SolrConstants.Fields.ContentFields;
import by.training.constants.SolrConstants.Fields.MetadataFields;
import by.training.exception.ValidationException;
import by.training.uploader.Uploadable;
import by.training.utility.Parser;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;

@Service("epubUploader")
public class EpubUploader implements Uploadable {

    private int progress;

    public EpubUploader() {
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public void uploadBook(Book book, String id, String uploader, String path)
            throws ValidationException {
        uploadMetadata(book, id, uploader);
        uploadContent(id, path);
    }

    @Override
    public void uploadMetadata(Book book, String id, String uploader) throws ValidationException {
        try (SolrClient client = new HttpSolrClient(METADATA_CORE_URI)) {
            SolrInputDocument inputDocument = new SolrInputDocument();

            inputDocument.setField(MetadataFields.ID, id);
            inputDocument.setField(MetadataFields.TITLE, book.getTitle());
            inputDocument.setField(MetadataFields.UPLOADER, uploader);
            inputDocument.setField(MetadataFields.DESCRIPTION,
                    book.getMetadata().getDescriptions());
            inputDocument.setField(MetadataFields.PUBLISHER, book.getMetadata().getPublishers());
            inputDocument.setField(MetadataFields.UPLOAD_DATE, new java.util.Date());

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
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    public void uploadContent(String metadataId, String path) throws ValidationException {
        try (SolrClient client = new HttpSolrClient(CONTENT_CORE_URI)) {

            Reader reader = new Reader();
            reader.setFullContent(path);
            reader.setMaxContentPerSection(1000);
            reader.setIsIncludingTextContent(true);

            int size = reader.getToc().getNavMap().getNavPoints().size();
            for (int page = 0; page < size; page++) {
                SolrInputDocument inputDocument = new SolrInputDocument();

                String content = reader.readSection(page).getSectionTextContent();

                inputDocument.setField(ContentFields.ID,
                        UUID.nameUUIDFromBytes((metadataId + page + 1).getBytes()).toString());
                inputDocument.setField(ContentFields.METADATA_ID, metadataId);
                inputDocument.setField(ContentFields.PAGE, page + 1);
                inputDocument.setField(ContentFields.CONTENT, content);

                client.add(inputDocument);

                progress = (int) Math.ceil((double) page * 100 / size);
            }

            client.commit(true, true);
        } catch (IOException | OutOfPagesException | ReadingException | SolrServerException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
