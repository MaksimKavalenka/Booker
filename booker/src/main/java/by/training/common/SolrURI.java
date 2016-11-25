package by.training.common;

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

    public String getFieldList() {
        return queries.get(FIELD_LIST);
    }

    public void setFieldList(String... fieldList) {
        StringBuilder _fieldList = new StringBuilder();

        int size = fieldList.length;
        for (int i = 0; i < size; i++) {
            _fieldList.append(fieldList[i]);

            if ((i + 1) < size) {
                _fieldList.append(",");
            }
        }

        queries.put(FIELD_LIST, _fieldList.toString());
    }

    public void addFieldList(String... fieldList) {
        String _fieldList = queries.get(FIELD_LIST);
        if (_fieldList != null) {
            StringBuilder __fieldList = new StringBuilder();

            int size = fieldList.length;
            for (int i = 0; i < size; i++) {
                __fieldList.append(fieldList[i]);

                if ((i + 1) < size) {
                    __fieldList.append(",");
                }
            }

            queries.put(FIELD_LIST, _fieldList + "," + __fieldList);
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
        queries.put(FILTER_QUERY, field + ":" + condition);
    }

    public void addFilterQuery(String field, String condition) {
        String filterQuery = queries.get(FILTER_QUERY);
        if (filterQuery != null) {
            queries.put(FILTER_QUERY,
                    filterQuery + "&" + FILTER_QUERY + "=" + field + ":" + condition);
        } else {
            setFilterQuery(field, condition);
        }
    }

    public void removeFilterQuery() {
        queries.remove(FILTER_QUERY);
    }

    public boolean isHighlight() {
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
        StringBuilder _highlightedFields = new StringBuilder();

        int size = highlightedFields.length;
        for (int i = 0; i < size; i++) {
            _highlightedFields.append(highlightedFields[i]);

            if ((i + 1) < size) {
                _highlightedFields.append(",");
            }
        }

        queries.put(HIGHLIGHTED_FIELDS, _highlightedFields.toString());
    }

    public void addHighlightedFields(String... highlightedFields) {
        String _highlightedFields = queries.get(HIGHLIGHTED_FIELDS);
        if (_highlightedFields != null) {
            StringBuilder __highlightedFields = new StringBuilder();

            int size = highlightedFields.length;
            for (int i = 0; i < size; i++) {
                __highlightedFields.append(highlightedFields[i]);

                if ((i + 1) < size) {
                    __highlightedFields.append(",");
                }
            }

            queries.put(HIGHLIGHTED_FIELDS, _highlightedFields + "," + __highlightedFields);
        } else {
            setFieldList(highlightedFields);
        }
    }

    public void removeHighlightedFields() {
        queries.remove(HIGHLIGHTED_FIELDS);
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
        StringBuilder _shards = new StringBuilder();

        int size = shards.length;
        for (int i = 0; i < size; i++) {
            _shards.append(shards[i]);

            if ((i + 1) < size) {
                _shards.append(",");
            }
        }

        queries.put(SHARDS, _shards.toString());
    }

    public void addShards(String... fieldList) {
        String _shards = queries.get(SHARDS);
        if (_shards != null) {
            StringBuilder __shards = new StringBuilder();

            int size = fieldList.length;
            for (int i = 0; i < size; i++) {
                __shards.append(fieldList[i]);

                if ((i + 1) < size) {
                    __shards.append(",");
                }
            }

            queries.put(SHARDS, _shards + "," + __shards);
        } else {
            setFieldList(fieldList);
        }
    }

    public void removeShards() {
        queries.remove(SHARDS);
    }

    public String getSorting() {
        return queries.get(SORTING);
    }

    public void setSorting(String field, Order order) {
        queries.put(SORTING, field + " " + order.toString());
    }

    public void addSorting(String field, Order order) {
        String sort = queries.get(SORTING);
        if (sort != null) {
            queries.put(SORTING, sort + "," + field + " " + order.toString());
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
            uri.append(entry.getKey() + "=" + entry.getValue());

            if (entries.hasNext()) {
                uri.append("&");
            }
        }

        return uri.toString();
    }

}
