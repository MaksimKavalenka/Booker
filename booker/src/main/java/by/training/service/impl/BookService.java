package by.training.service.impl;

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
        solrUri.setFieldList(MetadataFields.AUTHOR, MetadataFields.DESCRIPTION, MetadataFields.ID,
                MetadataFields.TITLE);
        solrUri.setSorting(MetadataFields.UPLOAD_DATE, Order.DESC);
        solrUri.setStart(DEFAULT_ROWS_COUNT * (page - 1));
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return JSONParser
                .getBooksResponse(restTemplate.getForObject(solrUri.toString(), String.class));
    }

    @Override
    public String getBookJson(String id) {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrUri.setFieldList(MetadataFields.FILE_NAME, MetadataFields.ID);
        solrUri.setFilterQuery(MetadataFields.ID, id);
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return JSONParser
                .getBookResponse(restTemplate.getForObject(solrUri.toString(), String.class));
    }

}
