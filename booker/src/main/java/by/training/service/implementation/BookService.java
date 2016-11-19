package by.training.service.implementation;

import static by.training.constants.DefaultConstants.DEFAULT_PAGES_COUNT;
import static by.training.constants.DefaultConstants.DEFAULT_ROWS_COUNT;
import static by.training.constants.DefaultConstants.DEFAULT_QUERY;
import static by.training.constants.SolrConstants.Core.*;
import static by.training.constants.SolrConstants.Fields.*;

import org.springframework.web.client.RestTemplate;

import by.training.common.Order;
import by.training.common.RequestHeader;
import by.training.common.SolrURI;
import by.training.common.WriterType;
import by.training.service.dao.BookServiceDAO;
import by.training.utility.JSONParser;

public class BookService implements BookServiceDAO {

    @Override
    public String getBooksJson(long page) {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrUri.setSorting(MetadataFields.UPLOAD_DATE, Order.DESC);
        solrUri.setStart(DEFAULT_ROWS_COUNT * (page - 1));
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return JSONParser
                .getBooksResponse(restTemplate.getForObject(solrUri.toString(), String.class));
    }

    @Override
    public String getBookJson(String metadataId, long page) {
        if ((page % DEFAULT_PAGES_COUNT) == 0) {
            --page;
        }
        String pageCondition = "(" + page + " " + (page + 1) + ")";

        SolrURI solrUri = new SolrURI(CONTENT_CORE_URI, RequestHeader.SELECT);
        solrUri.setFieldList(ContentFields.CONTENT);
        solrUri.addFilterQuery(ContentFields.METADATA_ID, metadataId);
        solrUri.addFilterQuery(ContentFields.PAGE, pageCondition);
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return JSONParser
                .getBookResponse(restTemplate.getForObject(solrUri.toString(), String.class));
    }

}
