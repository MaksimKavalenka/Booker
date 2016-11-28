package by.training.controller.rest;

import static by.training.constants.URLConstants.Key.ID_KEY;
import static by.training.constants.URLConstants.Rest.BOOK_STATUS_URL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.bean.ErrorMessage;
import by.training.entity.BookStatusEntity;
import by.training.parser.Parser;
import by.training.service.dao.BookStatusServiceDAO;
import by.training.utility.Secure;

@RestController
@RequestMapping(BOOK_STATUS_URL)
public class BookStatusRestController {

    @Autowired
    private BookStatusServiceDAO bookStatusServise;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createUpload(@RequestBody BookStatusEntity bookStatus,
            Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<Object>(new ErrorMessage(Parser.getErrorsMessages(errors)),
                    HttpStatus.BAD_REQUEST);
        }

        bookStatusServise.createBookStatus(bookStatus);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/unsuccessful", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUnsuccessfulBookStatuses() {
        bookStatusServise.deleteUnsuccessfulBookStatuses(Secure.getLoggedUser().getId());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/successful", method = RequestMethod.GET)
    public ResponseEntity<List<BookStatusEntity>> getSuccessfulBookStatuses() {
        List<BookStatusEntity> successfulBookStatuses = bookStatusServise
                .getSuccessfulBookStatuses(Secure.getLoggedUser().getId());
        return new ResponseEntity<List<BookStatusEntity>>(successfulBookStatuses, HttpStatus.OK);
    }

    @RequestMapping(value = "/unsuccessful", method = RequestMethod.GET)
    public ResponseEntity<List<BookStatusEntity>> getUnsuccessfulBookStatuses() {
        List<BookStatusEntity> unsuccessfulBookStatuses = bookStatusServise
                .getUnsuccessfulBookStatuses(Secure.getLoggedUser().getId());
        return new ResponseEntity<List<BookStatusEntity>>(unsuccessfulBookStatuses, HttpStatus.OK);
    }

    @RequestMapping(value = "/check" + ID_KEY, method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkBookStatus(@PathVariable("id") String id) {
        boolean existsBookStatus = bookStatusServise.checkBookStatuses(id);
        return new ResponseEntity<Boolean>(existsBookStatus, HttpStatus.OK);
    }

}
