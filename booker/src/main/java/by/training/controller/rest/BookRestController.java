package by.training.controller.rest;

import static by.training.constants.URLConstants.Key.ID_KEY;
import static by.training.constants.URLConstants.Rest.BOOKS_URL;

import javax.websocket.server.PathParam;

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

    @RequestMapping(value = "", params = "page", method = RequestMethod.GET)
    public ResponseEntity<String> getBooks(@PathParam("page") long page) {
        String books = bookService.getBooksJson(page);
        return new ResponseEntity<String>(books, HttpStatus.OK);
    }

    @RequestMapping(value = ID_KEY, method = RequestMethod.GET)
    public ResponseEntity<String> getBook(@PathVariable("id") String id) {
        String book = bookService.getBookJson(id);
        return new ResponseEntity<String>(book, HttpStatus.OK);
    }

}
