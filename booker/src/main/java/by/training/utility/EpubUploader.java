package by.training.utility;

import static by.training.constants.SolrConstants.Cores.*;
import static by.training.constants.SolrConstants.Fields.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import by.training.exception.ValidationException;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.domain.Resource;

public abstract class EpubUploader {

    public static void uploadBook(Book book, String id, String uploader)
            throws ValidationException {
        uploadMetadata(book, id, uploader);
        uploadContent(book, id);
    }

    public static void uploadMetadata(Book book, String id, String uploader)
            throws ValidationException {
        try (SolrClient client = new HttpSolrClient(METADATA_CORE_URI)) {
            SolrInputDocument inputDocument = new SolrInputDocument();

            inputDocument.setField(MetadataFields.ID, id);
            inputDocument.setField(MetadataFields.TITLE, book.getTitle());
            inputDocument.setField(MetadataFields.UPLOADER, uploader);
            inputDocument.setField(MetadataFields.DESCRIPTION,
                    book.getMetadata().getDescriptions());
            inputDocument.setField(MetadataFields.PUBLISHER, book.getMetadata().getPublishers());
            inputDocument.setField(MetadataFields.UPLOADING_DATE, new java.util.Date());

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
                                Parser.parse(date.getValue()));
                        break;
                    case PUBLICATION:
                        inputDocument.setField(MetadataFields.PUBLICATION_DATE,
                                Parser.parse(date.getValue()));
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

    private static void uploadContent(Book book, String metadataId) throws ValidationException {
        try (SolrClient client = new HttpSolrClient(CONTENT_CORE_URI)) {

            List<Resource> contents = book.getContents();
            for (int chapter = 0; chapter < contents.size(); chapter++) {
                SolrInputDocument inputDocument = new SolrInputDocument();

                inputDocument.setField(ContentFields.METADATA_ID, metadataId);

                InputStream in = contents.get(chapter).getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                try {
                    String line;
                    StringBuilder content = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        content.append(line + "\n").toString();
                    }

                    inputDocument.setField(ContentFields.CONTENT, content.toString());

                    inputDocument.setField(ContentFields.ID, UUID
                            .nameUUIDFromBytes((metadataId + chapter + 1).getBytes()).toString());
                    inputDocument.setField(ContentFields.CHAPTER, chapter + 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                client.add(inputDocument);
            }

            client.commit(true, true);
        } catch (IOException | SolrServerException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
