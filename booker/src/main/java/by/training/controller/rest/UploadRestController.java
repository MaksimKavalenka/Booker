package by.training.controller.rest;

import static by.training.constants.UploadConstants.*;
import static by.training.constants.URLConstants.Rest.UPLOAD_URL;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

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
import by.training.exception.SecureException;
import by.training.exception.UploadException;
import by.training.exception.ValidationException;
import by.training.solr.uploader.SolrUploadable;
import by.training.utility.Secure;
import by.training.utility.Utility;

@RestController
@MultipartConfig
@RequestMapping(UPLOAD_URL)
public class UploadRestController {

    @Autowired
    @Qualifier("epubSolrUploader")
    private SolrUploadable uploadable;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> uploadFile(
            @RequestParam(value = "file") MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();

            String id = Secure.encodeFilePassword(multipartFile.getInputStream());
            String directoryPath = Path.BOOKS + "/" + id;
            String filePath = directoryPath + "/" + fileName;

            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdir();
            }

            try (BufferedInputStream in = new BufferedInputStream(multipartFile.getInputStream())) {
                Utility.uploadFile(in, filePath);
            }

            uploadable.upload(directoryPath, fileName, id, Secure.getLoggedUser().getLogin());

            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (IOException | SecureException | UploadException | ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public ResponseEntity<Integer> getUploadProgress() {
        return new ResponseEntity<Integer>(uploadable.getProgress(), HttpStatus.OK);
    }

}
