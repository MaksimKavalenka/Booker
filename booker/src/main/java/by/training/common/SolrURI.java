package by.training.common;

import static by.training.constants.DelimiterConstants.*;
import static by.training.constants.SolrConstants.Query.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SolrURI {

    private String              coreUri;
    private RequestHeader       requestHeader;

    private Map<String, String> queries = new HashMap<>(8);

    public SolrURI(String coreUri, RequestHeader requestHeader) {
        this.coreUri = coreUri;
        this.requestHeader = requestHeader;
    }

    public String getCoreUri() {
        return coreUri;
    }

    public void setCoreUri(String coreUri) {
        this.coreUri = coreUri;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public boolean isFaceted() {
        return Boolean.valueOf(queries.get(FACET));
    }

    public void setFacet(boolean facet) {
        if (facet) {
            queries.put(FACET, String.valueOf(facet));
        } else {
            removeFacet();
        }
    }

    public void removeFacet() {
        queries.remove(FACET);
    }

    public String getFacetedFields() {
        return queries.get(FACETED_FIELDS);
    }

    public void setFacetedFields(String... facetedFields) {
        StringBuilder _facetedFields = new StringBuilder(facetedFields[0]);

        int size = facetedFields.length;
        for (int i = 1; i < size; i++) {
            _facetedFields.append(AMPERSAND_DELIMITER).append(FACETED_FIELDS)
                    .append(EQUAL_DELIMITER).append(facetedFields[i]);
        }

        queries.put(FACETED_FIELDS, _facetedFields.toString());
    }

    public void addFacetedFields(String... facetedFields) {
        String _facetedFields = queries.get(FACETED_FIELDS);
        if (_facetedFields != null) {
            StringBuilder __facetedFields = new StringBuilder();

            int size = facetedFields.length;
            for (int i = 0; i < size; i++) {
                __facetedFields.append(AMPERSAND_DELIMITER).append(FACETED_FIELDS)
                        .append(EQUAL_DELIMITER).append(facetedFields[i]);
            }

            queries.put(FACETED_FIELDS, _facetedFields + __facetedFields);
        } else {
            setFacetedFields(facetedFields);
        }
    }

    public void removeFacetedFields() {
        queries.remove(FACETED_FIELDS);
    }

    public String getFacetRangeEnd() {
        return queries.get(FACET_RANGE_END);
    }

    public void setFacetRangeEnd(String endDate) {
        queries.put(FACET_RANGE_END, endDate);
    }

    public void removeFacetRangeEnd() {
        queries.remove(FACET_RANGE_END);
    }

    public String getFacetRangeGap() {
        return queries.get(FACET_RANGE_GAP);
    }

    public void setFacetRangeGap(String gap) {
        queries.put(FACET_RANGE_GAP, gap);
    }

    public void removeFacetRangeGap() {
        queries.remove(FACET_RANGE_GAP);
    }

    public String getFacetRangeStart() {
        return queries.get(FACET_RANGE_START);
    }

    public void setFacetRangeStart(String startDate) {
        queries.put(FACET_RANGE_START, startDate);
    }

    public void removeFacetRangeStart() {
        queries.remove(FACET_RANGE_START);
    }

    public String getFieldList() {
        return queries.get(FIELD_LIST);
    }

    public void setFieldList(String... fieldList) {
        StringBuilder _fieldList = new StringBuilder(fieldList[0]);

        int size = fieldList.length;
        for (int i = 1; i < size; i++) {
            _fieldList.append(COMMA_DELIMITER).append(fieldList[i]);
        }

        queries.put(FIELD_LIST, _fieldList.toString());
    }

    public void addFieldList(String... fieldList) {
        String _fieldList = queries.get(FIELD_LIST);
        if (_fieldList != null) {
            StringBuilder __fieldList = new StringBuilder();

            int size = fieldList.length;
            for (int i = 0; i < size; i++) {
                __fieldList.append(COMMA_DELIMITER).append(fieldList[i]);
            }

            queries.put(FIELD_LIST, _fieldList + __fieldList);
        } else {
            setFieldList(fieldList);
        }
    }

    public void removeFieldList() {
        queries.remove(FIELD_LIST);
    }

    public String getFilterQuery() {
        return queries.get(FILTER_QUERY);
    }

    public void setFilterQuery(String field, String condition) {
        queries.put(FILTER_QUERY, field + COLON_DELIMITER + condition);
    }

    public void addFilterQuery(String field, String condition) {
        String filterQuery = queries.get(FILTER_QUERY);
        if (filterQuery != null) {
            queries.put(FILTER_QUERY, filterQuery + AMPERSAND_DELIMITER + FILTER_QUERY
                    + EQUAL_DELIMITER + field + COLON_DELIMITER + condition);
        } else {
            setFilterQuery(field, condition);
        }
    }

    public void removeFilterQuery() {
        queries.remove(FILTER_QUERY);
    }

    public boolean isHighlighted() {
        return Boolean.valueOf(queries.get(HIGHLIGHT));
    }

    public void setHighlight(boolean highlight) {
        if (highlight) {
            queries.put(HIGHLIGHT, String.valueOf(highlight));
        } else {
            removeHighlight();
        }
    }

    public void removeHighlight() {
        queries.remove(HIGHLIGHT);
    }

    public String getHighlightedFields() {
        return queries.get(HIGHLIGHTED_FIELDS);
    }

    public void setHighlightedFields(String... highlightedFields) {
        StringBuilder _highlightedFields = new StringBuilder(highlightedFields[0]);

        int size = highlightedFields.length;
        for (int i = 1; i < size; i++) {
            _highlightedFields.append(COMMA_DELIMITER).append(highlightedFields[i]);
        }

        queries.put(HIGHLIGHTED_FIELDS, _highlightedFields.toString());
    }

    public void addHighlightedFields(String... highlightedFields) {
        String _highlightedFields = queries.get(HIGHLIGHTED_FIELDS);
        if (_highlightedFields != null) {
            StringBuilder __highlightedFields = new StringBuilder();

            int size = highlightedFields.length;
            for (int i = 0; i < size; i++) {
                __highlightedFields.append(COMMA_DELIMITER).append(highlightedFields[i]);
            }

            queries.put(HIGHLIGHTED_FIELDS, _highlightedFields + __highlightedFields);
        } else {
            setHighlightedFields(highlightedFields);
        }
    }

    public void removeHighlightedFields() {
        queries.remove(HIGHLIGHTED_FIELDS);
    }

    public String getRangeFacetedFields() {
        return queries.get(FACET_RANGE);
    }

    public void setRangeFacetedFields(String... rangeFacetedFields) {
        StringBuilder _rangeFacetedFields = new StringBuilder(rangeFacetedFields[0]);

        int size = rangeFacetedFields.length;
        for (int i = 1; i < size; i++) {
            _rangeFacetedFields.append(AMPERSAND_DELIMITER).append(FACET_RANGE)
                    .append(EQUAL_DELIMITER).append(rangeFacetedFields[i]);
        }

        queries.put(FACET_RANGE, _rangeFacetedFields.toString());
    }

    public void addRangeFacetedFields(String... rangeFacetedFields) {
        String _rangeFacetedFields = queries.get(FACET_RANGE);
        if (_rangeFacetedFields != null) {
            StringBuilder __rangeFacetedFields = new StringBuilder();

            int size = rangeFacetedFields.length;
            for (int i = 0; i < size; i++) {
                __rangeFacetedFields.append(AMPERSAND_DELIMITER).append(FACET_RANGE)
                        .append(EQUAL_DELIMITER).append(rangeFacetedFields[i]);
            }

            queries.put(FACET_RANGE, _rangeFacetedFields + __rangeFacetedFields);
        } else {
            setFacetedFields(rangeFacetedFields);
        }
    }

    public void removeRangeFacetedFields() {
        queries.remove(FACET_RANGE);
    }

    public long getRows() {
        String rows = queries.get(ROWS);
        return rows != null ? Long.valueOf(rows) : 0;
    }

    public void setRows(long rows) {
        queries.put(ROWS, String.valueOf(rows));
    }

    public void removeRows() {
        queries.remove(ROWS);
    }

    public String getShards() {
        return queries.get(SHARDS);
    }

    public void setShards(String... shards) {
        StringBuilder _shards = new StringBuilder(shards[0]);

        int size = shards.length;
        for (int i = 1; i < size; i++) {
            _shards.append(COMMA_DELIMITER).append(shards[i]);
        }

        queries.put(SHARDS, _shards.toString());
    }

    public void addShards(String... shards) {
        String _shards = queries.get(SHARDS);
        if (_shards != null) {
            StringBuilder __shards = new StringBuilder();

            int size = shards.length;
            for (int i = 0; i < size; i++) {
                __shards.append(COMMA_DELIMITER).append(shards[i]);
            }

            queries.put(SHARDS, _shards + __shards);
        } else {
            setShards(shards);
        }
    }

    public void removeShards() {
        queries.remove(SHARDS);
    }

    public String getSorting() {
        return queries.get(SORTING);
    }

    public void setSorting(String field, Order order) {
        queries.put(SORTING, field + SPACE_DELIMITER + order.toString());
    }

    public void addSorting(String field, Order order) {
        String sort = queries.get(SORTING);
        if (sort != null) {
            queries.put(SORTING,
                    sort + COMMA_DELIMITER + field + SPACE_DELIMITER + order.toString());
        } else {
            setSorting(field, order);
        }
    }

    public void removeSorting() {
        queries.remove(SORTING);
    }

    public long getStart() {
        String start = queries.get(START);
        return start != null ? Long.valueOf(start) : 0;
    }

    public void setStart(long start) {
        queries.put(START, String.valueOf(start));
    }

    public void removeStart() {
        queries.remove(START);
    }

    public String getQuery() {
        return queries.get(QUERY);
    }

    public void setQuery(String query) {
        queries.put(QUERY, query);
    }

    public void removeQuery() {
        queries.remove(QUERY);
    }

    public WriterType getWriterType() {
        String writerType = queries.get(WRITER_TYPE);
        return writerType != null ? WriterType.valueOf(writerType) : null;
    }

    public void setWriterType(WriterType writerType) {
        queries.put(WRITER_TYPE, writerType.toString());
    }

    public void removeWriterType() {
        queries.remove(WRITER_TYPE);
    }

    @Override
    public String toString() {
        StringBuilder uri = new StringBuilder(coreUri + requestHeader.toString());

        Iterator<Entry<String, String>> entries = queries.entrySet().iterator();
        while (entries.hasNext()) {

            Entry<String, String> entry = entries.next();
            uri.append(entry.getKey()).append(EQUAL_DELIMITER).append(entry.getValue());

            if (entries.hasNext()) {
                uri.append(AMPERSAND_DELIMITER);
            }
        }

        return uri.toString();
    }

}
