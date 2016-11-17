package by.training.controller.rest;

import static by.training.constants.URLConstants.PAGE_KEY;
import static by.training.constants.URLConstants.Rest.BOOKS_URL;
import static by.training.constants.URLConstants.Rest.JSON_EXT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.service.dao.BookServiceDAO;

@RestController
@RequestMapping(BOOKS_URL)
public class BookRestController {

    @Autowired
    private BookServiceDAO bookService;

    @RequestMapping(value = PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<String> getBooks(@PathVariable("page") int page) {
        String books = bookService.getBooksJson(page);
        return new ResponseEntity<String>(books, HttpStatus.OK);
    }

}
