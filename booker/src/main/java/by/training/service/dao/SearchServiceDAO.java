package by.training.service.dao;

public interface SearchServiceDAO {

    String getSearchResultJson(String query, long page);

    String getSuggestionsJson(String query);

    String getFacetsJson();

}
