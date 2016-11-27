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
import by.training.parser.solr.json.SolrJSONSearchParser;
import by.training.service.dao.SearchServiceDAO;

@Service("searchService")
public class SearchService implements SearchServiceDAO {

    @Override
    public String getSearchResultJson(String query, long page) {
        SolrURI solrUri = new SolrURI(CONTENT_CORE_URI, RequestHeader.SELECT);
        solrUri.setFieldList(ContentFields.CONTENT, ContentFields.METADATA_ID, ContentFields.PAGE,
                MetadataFields.AUTHOR, MetadataFields.DESCRIPTION, MetadataFields.ID,
                MetadataFields.TITLE);
        solrUri.setHighlight(true);
        solrUri.setHighlightedFields(ContentFields.CONTENT, MetadataFields.AUTHOR,
                MetadataFields.DESCRIPTION, MetadataFields.TITLE);
        solrUri.setRows(DEFAULT_ROWS_COUNT);
        solrUri.setShards(CONTENT_CORE_URI, METADATA_CORE_URI);
        solrUri.setStart(DEFAULT_ROWS_COUNT * (page - 1));
        solrUri.setQuery(query);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return SolrJSONSearchParser.getSearchResultResponse(
                restTemplate.getForObject(solrUri.toString(), String.class));
    }

    @Override
    public String getFacetedSearchResultJson(long page, String facets) {
        SolrURI solrUri = new SolrURI(METADATA_CORE_URI, RequestHeader.SELECT);
        solrUri.setFieldList(ContentFields.CONTENT, ContentFields.METADATA_ID, ContentFields.PAGE,
                MetadataFields.AUTHOR, MetadataFields.DESCRIPTION, MetadataFields.ID,
                MetadataFields.TITLE);
        solrUri.setHighlight(true);
        solrUri.setHighlightedFields(ContentFields.CONTENT, MetadataFields.AUTHOR,
                MetadataFields.DESCRIPTION, MetadataFields.TITLE);
        solrUri.setRows(DEFAULT_ROWS_COUNT);
        solrUri.setStart(DEFAULT_ROWS_COUNT * (page - 1));
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        SolrJSONSearchParser.addFacetToSolrUri(solrUri, facets, MetadataFields.AUTHOR);
        SolrJSONSearchParser.addFacetToSolrUri(solrUri, facets, MetadataFields.PUBLISHER);
        SolrJSONSearchParser.addFacetToSolrUri(solrUri, facets, MetadataFields.UPLOADER);

        RestTemplate restTemplate = new RestTemplate();
        return SolrJSONSearchParser.getSearchResultResponse(
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
