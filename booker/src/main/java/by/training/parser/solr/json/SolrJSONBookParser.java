package by.training.parser.solr.json;

import static by.training.constants.DefaultConstants.DEFAULT_ROWS_COUNT;
import static by.training.constants.SolrConstants.Key.DOCS_KEY;
import static by.training.constants.SolrConstants.Key.NUM_FOUND_KEY;
import static by.training.constants.SolrConstants.Key.RESPONSE_KEY;
import static by.training.constants.SolrConstants.Key.START_KEY;

import org.json.JSONArray;
import org.json.JSONObject;

import by.training.constants.SolrConstants.Fields.ContentFields;
import by.training.constants.SolrConstants.Fields.MetadataFields;

public class SolrJSONBookParser {

    public static String getBooksResponse(String metadataJson) {
        JSONObject jsonObject = new JSONObject(metadataJson);
        JSONObject response = jsonObject.getJSONObject(RESPONSE_KEY);

        int page = response.getInt(START_KEY) / DEFAULT_ROWS_COUNT + 1;
        response.put(ContentFields.PAGE, page);
        response.remove(START_KEY);

        int pagesCount = (int) Math
                .ceil((double) response.getInt(NUM_FOUND_KEY) / DEFAULT_ROWS_COUNT);
        response.put(MetadataFields.PAGES_COUNT, pagesCount);
        response.remove(NUM_FOUND_KEY);

        return response.toString();
    }

    public static String getBookCustomResponse(String metadataJson, String contentJson) {
        JSONObject jsonMetadataObject = new JSONObject(metadataJson);
        JSONObject metadataResponse = jsonMetadataObject.getJSONObject(RESPONSE_KEY);
        JSONArray metadataDocs = metadataResponse.getJSONArray(DOCS_KEY);

        JSONObject jsonContentObject = new JSONObject(contentJson);
        JSONObject contentResponse = jsonContentObject.getJSONObject(RESPONSE_KEY);
        JSONArray contentDocs = contentResponse.getJSONArray(DOCS_KEY);

        JSONObject response = new JSONObject(metadataDocs.get(0).toString());
        response.putOpt(ContentFields.CONTENT, contentDocs);
        return response.toString();
    }

    public static String getBookStandardResponse(String metadataJson) {
        JSONObject jsonObject = new JSONObject(metadataJson);
        JSONObject response = jsonObject.getJSONObject(RESPONSE_KEY);
        JSONArray docs = response.getJSONArray(DOCS_KEY);
        return docs.get(0).toString();
    }

}
