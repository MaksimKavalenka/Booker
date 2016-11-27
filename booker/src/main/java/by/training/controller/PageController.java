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

    @RequestMapping(value = {LOGIN_URL, REGISTER_URL, UPLOAD_BOOKS_URL, SUCCESSFUL_UPLOADS_URL,
            UNSUCCESSFUL_UPLOADS_URL, BOOK_STANDARD_URL}, method = RequestMethod.GET)

    public String withoutParamsPages() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = {FACETED_SEARCH_URL, BOOKS_URL,
            BOOK_CUSTOM_URL}, params = "page", method = RequestMethod.GET)
    public String withPageParamPages() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = {SEARCH_URL}, params = {"query", "page"}, method = RequestMethod.GET)
    public String withQueryAndPageParamsPages() {
        return DEFAULT_PATH;
    }

}
