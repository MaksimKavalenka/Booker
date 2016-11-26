package by.training.parser.solr.json;

import static by.training.constants.DefaultConstants.DEFAULT_ROWS_COUNT;
import static by.training.constants.SolrConstants.Key.*;

import org.json.JSONArray;
import org.json.JSONObject;

import by.training.constants.SolrConstants.Fields.ContentFields;
import by.training.constants.SolrConstants.Fields.MetadataFields;

public class SolrJSONSearchParser {

    public static String getSearchResultResponse(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = jsonObject.getJSONObject(RESPONSE_KEY);
        response.remove(MAX_SCORE);

        int page = response.getInt(START_KEY) / DEFAULT_ROWS_COUNT + 1;
        response.put(ContentFields.PAGE, page);
        response.remove(START_KEY);

        int pagesCount = (int) Math
                .ceil((double) response.getInt(NUM_FOUND_KEY) / DEFAULT_ROWS_COUNT);
        response.put(MetadataFields.PAGES_COUNT, pagesCount);

        JSONObject highlighting = jsonObject.getJSONObject(HIGHLIGHTING_KEY);
        JSONArray docs = response.getJSONArray(DOCS_KEY);
        for (int i = 0; i < docs.length(); i++) {
            JSONObject doc = docs.getJSONObject(i);

            if (!doc.isNull(ContentFields.METADATA_ID)) {
                String id = doc.getString(ContentFields.ID);
                JSONObject highlight = highlighting.getJSONObject(id);

                setHighlight(doc, highlight, ContentFields.CONTENT);

            } else {
                String id = doc.getString(MetadataFields.ID);
                JSONObject highlight = highlighting.getJSONObject(id);

                setHighlight(doc, highlight, MetadataFields.AUTHOR);
                setHighlight(doc, highlight, MetadataFields.DESCRIPTION);
                setHighlight(doc, highlight, MetadataFields.TITLE);
            }
        }

        return response.toString();
    }

    public static String getSuggestionsResponse(String json) {
        String response = "";
        JSONObject jsonObject = new JSONObject(json);

        if (!jsonObject.isNull(SPELLCHECK_KEY)) {
            JSONObject spellcheck = jsonObject.getJSONObject(SPELLCHECK_KEY);

            if (spellcheck.getJSONArray(SUGGESTIONS_KEY).length() > 0) {
                JSONObject suggestions = spellcheck.getJSONArray(SUGGESTIONS_KEY).getJSONObject(1);
                JSONArray suggestionList = suggestions.getJSONArray(SUGGESTION_KEY);
                response = suggestionList.toString();
            }
        }

        return response;
    }

    public static String getFacetsResponse(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject facetCounts = jsonObject.getJSONObject(FACET_COUNTS_KEY);
        JSONObject facetFields = facetCounts.getJSONObject(FACET_FIELDS_KEY);

        setFacetArray(facetFields, MetadataFields.AUTHOR);
        setFacetArray(facetFields, MetadataFields.PUBLISHER);
        setFacetArray(facetFields, MetadataFields.UPLOADER);

        return facetFields.toString();
    }

    private static void setHighlight(JSONObject doc, JSONObject highlight, String field) {
        if (!highlight.isNull(field)) {
            String newField = highlight.getJSONArray(field).getString(0);
            doc.put(field, newField);
        }
    }

    private static void setFacetArray(JSONObject facetFields, String field) {
        final String NAME = "name";
        final String VALUE = "value";

        if (!facetFields.isNull(field)) {
            JSONArray jsonArray = new JSONArray();

            JSONArray authors = facetFields.getJSONArray(field);
            for (int i = 0; i < authors.length(); i = i + 2) {
                JSONObject author = new JSONObject();
                author.put(NAME, authors.getString(i));
                author.put(VALUE, authors.getLong(i + 1));
                jsonArray.put(author);
            }

            facetFields.put(field, jsonArray);
        }
    }

}
