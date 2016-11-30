package by.training.service.impl;

import static by.training.constants.DefaultConstants.DEFAULT_ROWS_COUNT;
import static by.training.constants.DefaultConstants.DEFAULT_QUERY;
import static by.training.constants.SolrConstants.Core.CONTENT_CORE_URI;
import static by.training.constants.SolrConstants.Core.METADATA_CORE_URI;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import by.training.common.RequestHeader;
import by.training.common.SolrURI;
import by.training.common.WriterType;
import by.training.constants.SolrConstants.Fields.ContentFields;
import by.training.constants.SolrConstants.Fields.MetadataFields;
import by.training.parser.solr.json.SolrJSONParser;
import by.training.parser.solr.json.SolrJSONSearchParser;
import by.training.service.dao.SearchServiceDAO;

@Service("searchService")
public class SearchService implements SearchServiceDAO {

    @Override
    public String getSearchResultJson(String query, long page) {
        SolrURI solrMetadataUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrMetadataUri.setFieldList(MetadataFields.AUTHOR, MetadataFields.DESCRIPTION,
                MetadataFields.ID, MetadataFields.TITLE);
        solrMetadataUri.setHighlight(true);
        solrMetadataUri.setHighlightedFields(MetadataFields.AUTHOR, MetadataFields.DESCRIPTION,
                MetadataFields.TITLE);
        solrMetadataUri.setRows(DEFAULT_ROWS_COUNT);
        solrMetadataUri.setStart(DEFAULT_ROWS_COUNT * (page - 1));
        solrMetadataUri.setQuery(query);
        solrMetadataUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        String metadataResponse = SolrJSONSearchParser.getSearchResultResponse(
                restTemplate.getForObject(solrMetadataUri.toString(), String.class));

        long docs = SolrJSONParser.getRowsCount(metadataResponse);
        if (docs < DEFAULT_ROWS_COUNT) {
            SolrURI solrContentUri = new SolrURI(CONTENT_CORE_URI, RequestHeader.SELECT);
            solrContentUri.setFieldList(ContentFields.CONTENT, ContentFields.ID,
                    ContentFields.METADATA_ID, ContentFields.PAGE);
            solrContentUri.setHighlight(true);
            solrContentUri.setHighlightedFields(ContentFields.CONTENT);
            solrContentUri.setQuery(query);
            solrContentUri.setWriterType(WriterType.JSON);

            long start = DEFAULT_ROWS_COUNT * (page - 1)
                    - SolrJSONParser.getRowsCount(metadataResponse);
            if (start < 0) {
                solrContentUri.setRows(DEFAULT_ROWS_COUNT - docs);
                solrContentUri.setStart(0);
            } else {
                solrContentUri.setRows(DEFAULT_ROWS_COUNT);
                solrContentUri.setStart(start);
            }

            String contentResponse = SolrJSONSearchParser.getSearchResultResponse(
                    restTemplate.getForObject(solrContentUri.toString(), String.class));
            return SolrJSONParser.uniteResponses(metadataResponse, contentResponse);
        }

        return metadataResponse;
    }

    @Override
    public String getFacetedSearchResultJson(String query, String facets, long page) {
        SolrURI solrMetadataUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrMetadataUri.setFieldList(MetadataFields.AUTHOR, MetadataFields.DESCRIPTION,
                MetadataFields.ID, MetadataFields.TITLE);

        solrMetadataUri.setHighlight(true);
        solrMetadataUri.setHighlightedFields(MetadataFields.AUTHOR, MetadataFields.DESCRIPTION,
                MetadataFields.TITLE);
        solrMetadataUri.setRows(DEFAULT_ROWS_COUNT);
        solrMetadataUri.setStart(DEFAULT_ROWS_COUNT * (page - 1));
        solrMetadataUri.setQuery(query);
        solrMetadataUri.setWriterType(WriterType.JSON);

        SolrJSONParser.addFacetToSolrUri(solrMetadataUri, facets, MetadataFields.AUTHOR);
        SolrJSONParser.addFacetToSolrUri(solrMetadataUri, facets, MetadataFields.PUBLISHER);
        SolrJSONParser.addFacetToSolrUri(solrMetadataUri, facets, MetadataFields.UPLOADER);

        RestTemplate restTemplate = new RestTemplate();
        String metadataResponse = SolrJSONSearchParser.getSearchResultResponse(
                restTemplate.getForObject(solrMetadataUri.toString(), String.class));

        long docs = SolrJSONParser.getRowsCount(metadataResponse);
        if (docs < DEFAULT_ROWS_COUNT) {
            SolrURI solrIdUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
            solrIdUri.setFieldList(MetadataFields.ID);
            solrIdUri.setQuery(DEFAULT_QUERY);
            solrIdUri.setWriterType(WriterType.JSON);

            SolrJSONParser.addFacetToSolrUri(solrIdUri, facets, MetadataFields.AUTHOR);
            SolrJSONParser.addFacetToSolrUri(solrIdUri, facets, MetadataFields.PUBLISHER);
            SolrJSONParser.addFacetToSolrUri(solrIdUri, facets, MetadataFields.UPLOADER);

            String ids = restTemplate.getForObject(solrIdUri.toString(), String.class);

            SolrURI solrContentUri = new SolrURI(CONTENT_CORE_URI, RequestHeader.SELECT);
            solrContentUri.setFieldList(ContentFields.CONTENT, ContentFields.ID,
                    ContentFields.METADATA_ID, ContentFields.PAGE);
            solrContentUri.setHighlight(true);
            solrContentUri.setHighlightedFields(ContentFields.CONTENT);
            solrContentUri.setQuery(query);
            solrContentUri.setWriterType(WriterType.JSON);

            SolrJSONParser.addIdToSolrUri(solrContentUri, ids);

            long start = DEFAULT_ROWS_COUNT * (page - 1)
                    - SolrJSONParser.getRowsCount(metadataResponse);
            if (start < 0) {
                solrContentUri.setRows(DEFAULT_ROWS_COUNT - docs);
                solrContentUri.setStart(0);
            } else {
                solrContentUri.setRows(DEFAULT_ROWS_COUNT);
                solrContentUri.setStart(start);
            }

            String contentResponse = SolrJSONSearchParser.getSearchResultResponse(
                    restTemplate.getForObject(solrContentUri.toString(), String.class));
            return SolrJSONParser.uniteResponses(metadataResponse, contentResponse);
        }

        return metadataResponse;
    }

    @Override
    public String getFacetedSearchResultJson(String facets, long page) {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrUri.setFieldList(MetadataFields.AUTHOR, MetadataFields.DESCRIPTION, MetadataFields.ID,
                MetadataFields.TITLE);
        solrUri.setRows(DEFAULT_ROWS_COUNT);
        solrUri.setStart(DEFAULT_ROWS_COUNT * (page - 1));
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        SolrJSONParser.addFacetToSolrUri(solrUri, facets, MetadataFields.AUTHOR);
        SolrJSONParser.addFacetToSolrUri(solrUri, facets, MetadataFields.PUBLISHER);
        SolrJSONParser.addFacetToSolrUri(solrUri, facets, MetadataFields.UPLOADER);

        RestTemplate restTemplate = new RestTemplate();
        return SolrJSONSearchParser.getFacetedSearchResultResponse(
                restTemplate.getForObject(solrUri.toString(), String.class));
    }

    @Override
    public String getSuggestionsJson(String query) {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SUGGEST);
        solrUri.setQuery(query);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return SolrJSONSearchParser.getSuggestionsResponse(
                restTemplate.getForObject(solrUri.toString(), String.class));
    }

    @Override
    public String getFacetsJson() {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrUri.setFacet(true);
        solrUri.setFacetedFields(MetadataFields.AUTHOR, MetadataFields.PUBLISHER,
                MetadataFields.UPLOADER);

        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return SolrJSONSearchParser
                .getFacetsResponse(restTemplate.getForObject(solrUri.toString(), String.class));
    }

}
