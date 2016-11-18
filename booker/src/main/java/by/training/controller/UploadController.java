package by.training.controller;

import static by.training.constants.MessageConstants.UPLOAD_FILE_ERROR_MESSAGE;
import static by.training.constants.UploadConstants.*;
import static by.training.constants.URLConstants.Rest.UPLOAD_URL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import by.training.bean.ErrorMessage;
import by.training.exception.ValidationException;
import by.training.utility.EpubUploader;
import by.training.utility.Secure;
import nl.siegmann.epublib.domain.Book;
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

            EpubReader epubReader = new EpubReader();
            Book book = epubReader.readEpub(fileInputStream);

            try (InputStream bookInputStream = book.getCoverImage().getInputStream()) {
                String id = Secure.encodeFileByMd5(fileInputStream);
                uploadFile(book.getCoverImage().getInputStream(), path + "/" + id);
                EpubUploader.uploadBook(book, id, Secure.getLoggedUser().getLogin());
            }

            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (IOException | NoSuchAlgorithmException | ValidationException e) {
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

}
