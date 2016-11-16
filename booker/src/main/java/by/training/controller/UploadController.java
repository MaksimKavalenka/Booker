package by.training.controller;

import static by.training.constants.MessageConstants.UPLOAD_FILE_ERROR_MESSAGE;
import static by.training.constants.SolrConstants.Collections.*;
import static by.training.constants.SolrConstants.Fields.*;
import static by.training.constants.UploadConstants.*;
import static by.training.constants.UrlConstants.Rest.UPLOAD_URL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.annotation.MultipartConfig;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import by.training.bean.ErrorMessage;
import by.training.exception.ValidationException;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

@Controller
@MultipartConfig
@RequestMapping(UPLOAD_URL)
public class UploadController {

    private static final String COVER = "cover.jpg";

    @RequestMapping(value = "/{type}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> uploadFile(@PathVariable("type") String type,
            @RequestParam(value = "file") MultipartFile file) {
        try {
            String path = Path.BOOKS_ROOT;
            switch (type) {
                case Type.EPUB:
                    EpubReader epubReader = new EpubReader();
                    Book book = epubReader.readEpub(file.getInputStream());

                    path += "/" + book.getMetadata().getIdentifiers().get(0).getValue();
                    new File(path).mkdir();

                    uploadFile(file.getInputStream(), path + "/" + file.getOriginalFilename());
                    uploadFile(book.getCoverImage().getInputStream(), path + "/" + COVER);

                    uploadMetadata(book);
                    break;
            }
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (IOException | ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    private void uploadFile(InputStream file, String path) throws ValidationException {
        try (OutputStream out = new FileOutputStream(new File(path))) {
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = file.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (FileNotFoundException e) {
            throw new ValidationException(UPLOAD_FILE_ERROR_MESSAGE);
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private void uploadMetadata(Book book) throws ValidationException {
        try {
            SolrClient client = new HttpSolrClient(METADATA_COLLECTION);

            SolrInputDocument inputDocument = new SolrInputDocument();
            inputDocument.setField(MetadataFields.ID,
                    book.getMetadata().getIdentifiers().get(0).getValue());
            inputDocument.setField(MetadataFields.TITLE, book.getMetadata().getFirstTitle());
            inputDocument.setField(MetadataFields.DESCRIPTION,
                    book.getMetadata().getDescriptions().get(0));
            inputDocument.setField(MetadataFields.PUBLISHER,
                    book.getMetadata().getPublishers().get(0));

            for (Author author : book.getMetadata().getAuthors()) {
                inputDocument.setField(MetadataFields.AUTHOR, author.toString());
            }

            client.add(inputDocument);
            client.commit(true, true);
            client.close();
        } catch (IOException | SolrServerException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
