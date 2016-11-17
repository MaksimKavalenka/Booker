package by.training.controller;

import static by.training.constants.MessageConstants.UPLOAD_FILE_ERROR_MESSAGE;
import static by.training.constants.SolrConstants.Cores.*;
import static by.training.constants.SolrConstants.Fields.*;
import static by.training.constants.UploadConstants.*;
import static by.training.constants.URLConstants.Rest.UPLOAD_URL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.MultipartConfig;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import by.training.bean.ErrorMessage;
import by.training.constants.RegexConstants;
import by.training.exception.ValidationException;
import by.training.utility.Secure;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.epub.EpubReader;

@Controller
@MultipartConfig
@RequestMapping(UPLOAD_URL)
public class UploadController {

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> uploadFile(
            @RequestParam(value = "file") MultipartFile file) {
        try (InputStream fileInputStream = file.getInputStream()) {
            String path = Path.COVER_ROOT;
            UUID uuid = UUID.randomUUID();

            EpubReader epubReader = new EpubReader();
            Book book = epubReader.readEpub(fileInputStream);

            try (InputStream bookInputStream = book.getCoverImage().getInputStream()) {
                uploadFile(book.getCoverImage().getInputStream(), path + "/" + uuid.toString());
                uploadMetadata(book, uuid);
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

    private void uploadMetadata(Book book, UUID uuid) throws ValidationException {
        try (SolrClient client = new HttpSolrClient(METADATA_CORE_URI)) {
            SolrInputDocument inputDocument = new SolrInputDocument();
            inputDocument.setField(MetadataFields.ID, uuid.toString());
            inputDocument.setField(MetadataFields.TITLE, book.getMetadata().getFirstTitle());
            inputDocument.setField(MetadataFields.UPLOADER, Secure.getLoggedUser().getLogin());

            Pattern pattern = Pattern.compile(RegexConstants.REGEX);
            List<Identifier> identifiers = book.getMetadata().getIdentifiers();
            List<String> strIdentifiers = new ArrayList<>(identifiers.size());
            for (Identifier identifier : book.getMetadata().getIdentifiers()) {
                Matcher matcher = pattern.matcher(identifier.getValue());
                if (matcher.matches()) {
                    strIdentifiers.add(identifier.getValue());
                }
            }
            inputDocument.setField(MetadataFields.ISBN, strIdentifiers);

            inputDocument.setField(MetadataFields.DESCRIPTION,
                    book.getMetadata().getDescriptions());

            List<Author> authors = book.getMetadata().getAuthors();
            List<String> strAuthors = new ArrayList<>(authors.size());
            for (Author author : authors) {
                strAuthors.add(author.toString());
            }
            inputDocument.setField(MetadataFields.AUTHOR, strAuthors);

            inputDocument.setField(MetadataFields.PUBLISHER, book.getMetadata().getPublishers());

            client.add(inputDocument);
            client.commit(true, true);
        } catch (IOException | SolrServerException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
