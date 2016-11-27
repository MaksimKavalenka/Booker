package by.training.service.impl;

import static by.training.constants.DefaultConstants.DEFAULT_PAGES_COUNT;
import static by.training.constants.DefaultConstants.DEFAULT_ROWS_COUNT;
import static by.training.constants.DelimiterConstants.SPACE_DELIMITER;
import static by.training.constants.DefaultConstants.DEFAULT_QUERY;
import static by.training.constants.SolrConstants.Core.*;
import static by.training.constants.SolrConstants.Fields.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import by.training.common.Order;
import by.training.common.RequestHeader;
import by.training.common.SolrURI;
import by.training.common.WriterType;
import by.training.parser.solr.json.SolrJSONBookParser;
import by.training.service.dao.BookServiceDAO;

@Service("bookService")
public class BookService implements BookServiceDAO {

    @Override
    public String getBooksJson(long page) {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrUri.setFieldList(MetadataFields.AUTHOR, MetadataFields.DESCRIPTION, MetadataFields.ID,
                MetadataFields.TITLE);
        solrUri.setRows(DEFAULT_ROWS_COUNT);
        solrUri.setSorting(MetadataFields.UPLOAD_DATE, Order.DESC);
        solrUri.setStart(DEFAULT_ROWS_COUNT * (page - 1));
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return SolrJSONBookParser
                .getBooksResponse(restTemplate.getForObject(solrUri.toString(), String.class));
    }

    @Override
    public String getBookCustomJson(String id, long page) {
        if ((page % DEFAULT_PAGES_COUNT) == 0) {
            --page;
        }

        SolrURI solrMetadataUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrMetadataUri.setFieldList(MetadataFields.ID, MetadataFields.TITLE,
                MetadataFields.PAGES_COUNT);
        solrMetadataUri.setFilterQuery(MetadataFields.ID, id);
        solrMetadataUri.setQuery(DEFAULT_QUERY);
        solrMetadataUri.setWriterType(WriterType.JSON);

        SolrURI solrContentUri = new SolrURI(CONTENT_CORE_URI, RequestHeader.SELECT);
        solrContentUri.setFieldList(ContentFields.CONTENT, ContentFields.PAGE);
        solrContentUri.addFilterQuery(ContentFields.METADATA_ID, id);
        solrContentUri.addFilterQuery(ContentFields.PAGE,
                "(" + page + SPACE_DELIMITER + (page + 1) + ")");
        solrContentUri.setQuery(DEFAULT_QUERY);
        solrContentUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        String metadataResponse = restTemplate.getForObject(solrMetadataUri.toString(),
                String.class);
        String contentResponse = restTemplate.getForObject(solrContentUri.toString(), String.class);

        return SolrJSONBookParser.getBookCustomResponse(metadataResponse, contentResponse);
    }

    @Override
    public String getBookStandardJson(String id) {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrUri.setFieldList(MetadataFields.FILE_NAME, MetadataFields.ID, MetadataFields.TITLE);
        solrUri.setFilterQuery(MetadataFields.ID, id);
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return SolrJSONBookParser.getBookStandardResponse(
                restTemplate.getForObject(solrUri.toString(), String.class));
    }

}
