package by.training.controller.rest;

import static by.training.constants.URLConstants.Rest.SEARCH_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.service.dao.SearchServiceDAO;

@RestController
@RequestMapping(SEARCH_URL)
public class SearchRestController {

    @Autowired
    private SearchServiceDAO searchService;

    @RequestMapping(value = "", params = {"query", "page"}, method = RequestMethod.GET)
    public ResponseEntity<String> getSearchResult(@Param("query") String query,
            @Param("page") long page) {
        String searchResult = searchService.getSearchResultJson(query, page);
        return new ResponseEntity<String>(searchResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/faceted", params = {"query", "page",
            "facets"}, method = RequestMethod.GET)
    public ResponseEntity<String> getFacetedSearchResult(@Param("query") String query,
            @Param("page") long page, @Param("facets") String facets) {
        System.err.println(facets);
        // String searchResult = searchService.getSearchResultJson(query, page);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/suggest", params = "query", method = RequestMethod.GET)
    public ResponseEntity<String> getSuggestions(@Param("query") String query) {
        String suggestions = searchService.getSuggestionsJson(query);
        return new ResponseEntity<String>(suggestions, HttpStatus.OK);
    }

    @RequestMapping(value = "/facets", method = RequestMethod.GET)
    public ResponseEntity<String> getFacets() {
        String facets = searchService.getFacetsJson();
        return new ResponseEntity<String>(facets, HttpStatus.OK);
    }

}
