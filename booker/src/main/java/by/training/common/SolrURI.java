package by.training.common;

import static by.training.constants.SolrConstants.Query.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SolrURI {

    private String              coreUri;
    private RequestHeader       requestHeader;

    private Map<String, String> queries = new HashMap<>(5);

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

    public String getQuery() {
        return queries.get(QUERY);
    }

    public void setQuery(String query) {
        queries.put(QUERY, query);
    }

    public void removeQuery() {
        queries.remove(QUERY);
    }

    public boolean isIndent() {
        return queries.get(INDENT) != null;
    }

    public void setIndent(boolean indent) {
        queries.put(INDENT, String.valueOf(indent));
    }

    public void removeIndent() {
        queries.remove(INDENT);
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
            queries.put(SORTING, field + " " + order.toString());
        }
    }

    public void removeSorting() {
        queries.remove(SORTING);
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
