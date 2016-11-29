package by.training.service.dao;

public interface SearchServiceDAO {

    String getSearchResultJson(String query, long page);

    String getFacetedSearchResultJson(String query, String facets, long page);

    String getFacetedSearchResultJson(String facets, long page);

    String getSuggestionsJson(String query);

    String getFacetsJson();

}
