package by.training.utility;

import static by.training.constants.DefaultConstants.DEFAULT_ROWS;

import org.json.JSONObject;

public abstract class JSONParser {

    private static final String NUM_FOUND_KEY   = "numFound";
    private static final String PAGE_KEY        = "page";
    private static final String PAGES_COUNT_KEY = "pagesCount";
    private static final String RESPONSE_KEY    = "response";
    private static final String START_KEY       = "start";

    public static String getResponse(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = jsonObject.getJSONObject(RESPONSE_KEY);

        int page = response.getInt(START_KEY) / DEFAULT_ROWS + 1;
        response.append(PAGE_KEY, page);
        response.remove(START_KEY);

        int pagesCount = (int) Math.ceil((double) response.getInt(NUM_FOUND_KEY) / DEFAULT_ROWS);
        response.append(PAGES_COUNT_KEY, pagesCount);
        response.remove(NUM_FOUND_KEY);

        return response.toString();
    }

}
