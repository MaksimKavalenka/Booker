package by.training.controller;

import static by.training.constants.DefaultConstants.*;
import static by.training.constants.URLConstants.Page.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = {"/", BOOKS_URL}, method = RequestMethod.GET)
    public String defaultPage() {
        return REDIRECT + BOOKS_URL + "?" + DEFAULT_PAGE;
    }

    @RequestMapping(value = {LOGIN_URL, REGISTER_URL, UPLOAD_BOOKS_URL}, method = RequestMethod.GET)
    public String editPages() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = {BOOKS_URL, BOOK_URL}, params = "page", method = RequestMethod.GET)
    public String booksPage() {
        return DEFAULT_PATH;
    }

}
