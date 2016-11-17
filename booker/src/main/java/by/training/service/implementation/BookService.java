package by.training.service.implementation;

import static by.training.constants.DefaultConstants.DEFAULT_ROWS;
import static by.training.constants.SolrConstants.Cores.METADATA_CORE_URI;
import static by.training.constants.SolrConstants.Fields.MetadataFields.*;

import org.springframework.web.client.RestTemplate;

import by.training.common.Order;
import by.training.common.RequestHeader;
import by.training.common.SolrURI;
import by.training.common.WriterType;
import by.training.service.dao.BookServiceDAO;
import by.training.utility.JSONParser;

public class BookService implements BookServiceDAO {

    @Override
    public String getBooksJson(int page) {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrUri.setQuery("*:*");
        solrUri.setStart(DEFAULT_ROWS * (page - 1));
        solrUri.setSorting(TITLE, Order.ASC);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return JSONParser.getResponse(restTemplate.getForObject(solrUri.toString(), String.class));
    }

}
