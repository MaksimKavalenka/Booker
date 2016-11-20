package by.training.utility;

import static by.training.constants.DefaultConstants.DEFAULT_ROWS_COUNT;
import static by.training.constants.SolrConstants.Key.*;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class JSONParser {

    public static String getBooksResponse(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = jsonObject.getJSONObject(RESPONSE_KEY);

        int page = response.getInt(START_KEY) / DEFAULT_ROWS_COUNT + 1;
        response.put(PAGE_KEY, page);
        response.remove(START_KEY);

        int pagesCount = (int) Math
                .ceil((double) response.getInt(NUM_FOUND_KEY) / DEFAULT_ROWS_COUNT);
        response.put(PAGES_COUNT_KEY, pagesCount);
        response.remove(NUM_FOUND_KEY);

        return response.toString();
    }

    public static String getBookResponse(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = jsonObject.getJSONObject(RESPONSE_KEY);
        JSONArray docs = response.getJSONArray(DOCS_KEY);
        return docs.get(0).toString();
    }

}
