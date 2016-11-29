package by.training.parser.solr.json;

import static by.training.constants.DefaultConstants.DEFAULT_ROWS_COUNT;
import static by.training.constants.DelimiterConstants.BRACKET_CLOSE_DELIMITER;
import static by.training.constants.DelimiterConstants.BRACKET_OPEN_DELIMITER;
import static by.training.constants.DelimiterConstants.SPACE_DELIMITER;
import static by.training.constants.SolrConstants.Key.DOCS_KEY;
import static by.training.constants.SolrConstants.Key.NUM_FOUND_KEY;
import static by.training.constants.SolrConstants.Key.RESPONSE_KEY;

import org.json.JSONArray;
import org.json.JSONObject;

import by.training.common.SolrURI;
import by.training.constants.SolrConstants.Fields.ContentFields;
import by.training.constants.SolrConstants.Fields.MetadataFields;

public abstract class SolrJSONParser {

    public static void addFacetToSolrUri(SolrURI solrUri, String facetsJson, String field) {
        JSONObject jsonObject = new JSONObject(facetsJson);

        if (!jsonObject.isNull(field) && jsonObject.getJSONArray(field).length() > 0) {
            JSONArray jsonArray = jsonObject.getJSONArray(field);

            StringBuilder condition = new StringBuilder(jsonArray.getString(0));
            for (int i = 1; i < jsonArray.length(); i++) {
                condition.append(SPACE_DELIMITER).append(jsonArray.getString(i));
            }

            solrUri.addFilterQuery(field, condition.toString());
        }
    }

    public static void addIdToSolrUri(SolrURI solrUri, String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = jsonObject.getJSONObject(RESPONSE_KEY);

        JSONArray docs = response.getJSONArray(DOCS_KEY);
        if (docs.length() > 0) {
            StringBuilder ids = new StringBuilder(BRACKET_OPEN_DELIMITER)
                    .append(docs.getJSONObject(0).getString(MetadataFields.ID));

            for (int i = 0; i < docs.length(); i++) {
                String id = docs.getJSONObject(i).getString(MetadataFields.ID);
                ids.append(SPACE_DELIMITER).append(id);
            }
            ids.append(BRACKET_CLOSE_DELIMITER);

            solrUri.addFilterQuery(ContentFields.METADATA_ID, ids.toString());
        }
    }

    public static String uniteResponses(String... responses) {
        JSONObject response = new JSONObject(responses[0]);
        long numFound = response.getLong(NUM_FOUND_KEY);

        for (int i = 1; i < responses.length; i++) {
            JSONObject _response = new JSONObject(responses[i]);
            numFound += _response.getLong(NUM_FOUND_KEY);

            for (int j = 0; j < _response.getJSONArray(DOCS_KEY).length(); j++) {
                response.getJSONArray(DOCS_KEY)
                        .put(_response.getJSONArray(DOCS_KEY).getJSONObject(j));
            }
        }

        response.put(NUM_FOUND_KEY, numFound);
        int pagesCount = (int) Math.ceil((double) numFound / DEFAULT_ROWS_COUNT);
        response.put(MetadataFields.PAGES_COUNT, pagesCount);

        return response.toString();
    }

    public static long getDocsCount(String responseJson) {
        JSONObject response = new JSONObject(responseJson);
        return response.getJSONArray(DOCS_KEY).length();
    }

    public static long getRowsCount(String responseJson) {
        JSONObject response = new JSONObject(responseJson);
        return response.getLong(NUM_FOUND_KEY);
    }

}
