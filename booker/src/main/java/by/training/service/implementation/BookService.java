package by.training.service.implementation;

import static by.training.constants.DefaultConstants.DEFAULT_ROWS;
import static by.training.constants.SolrConstants.Collections.METADATA_COLLECTION;

import org.springframework.web.client.RestTemplate;

import by.training.service.dao.BookServiceDAO;
import by.training.utility.JSONParser;

public class BookService implements BookServiceDAO {

    @Override
    public String getBooksJson(int page) {
        String uri = METADATA_COLLECTION + "/select?indent=on&q=*:*&start="
                + DEFAULT_ROWS * (page - 1) + "&wt=json";
        RestTemplate restTemplate = new RestTemplate();
        return JSONParser.getResponse(restTemplate.getForObject(uri, String.class));
    }

}
