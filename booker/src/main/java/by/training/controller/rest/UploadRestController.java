package by.training.controller.rest;

import static by.training.constants.MessageConstants.UPLOAD_FILE_ERROR_MESSAGE;
import static by.training.constants.UploadConstants.*;
import static by.training.constants.URLConstants.Rest.UPLOAD_URL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import by.training.bean.ErrorMessage;
import by.training.exception.ValidationException;
import by.training.uploader.Uploadable;
import by.training.utility.Secure;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

@RestController
@MultipartConfig
@RequestMapping(UPLOAD_URL)
public class UploadRestController {

    @Autowired
    @Qualifier("epubUploader")
    private Uploadable uploadable;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> uploadFile(
            @RequestParam(value = "file") MultipartFile file) {
        try {
            String id;
            String uuid = UUID.randomUUID().toString();

            try (InputStream in = file.getInputStream()) {
                uploadFile(in, Path.TEMP_ROOT + "/" + uuid);
            }

            File tempFile = new File(Path.TEMP_ROOT + "/" + uuid);

            try (InputStream in = new FileInputStream(tempFile)) {
                id = Secure.encodeFileByMd5(in);
            }

            try (InputStream in = new FileInputStream(tempFile)) {
                EpubReader epubReader = new EpubReader();
                Book book = epubReader.readEpub(in);

                try (InputStream bookInputStream = book.getCoverImage().getInputStream()) {
                    uploadFile(book.getCoverImage().getInputStream(), Path.COVER_ROOT + "/" + id);
                    uploadable.uploadBook(book, id, Secure.getLoggedUser().getLogin(),
                            Path.TEMP_ROOT + "/" + uuid);
                }
            }

            tempFile.delete();
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (IOException | NoSuchAlgorithmException | ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public ResponseEntity<Integer> getUploadProgress() {
        return new ResponseEntity<Integer>(uploadable.getProgress(), HttpStatus.OK);
    }

    private void uploadFile(InputStream in, String path) throws ValidationException {
        try (OutputStream out = new FileOutputStream(new File(path))) {
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (FileNotFoundException e) {
            throw new ValidationException(UPLOAD_FILE_ERROR_MESSAGE);
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
