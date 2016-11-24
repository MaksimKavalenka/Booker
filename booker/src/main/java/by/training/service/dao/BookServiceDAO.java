package by.training.service.dao;

public interface BookServiceDAO {

    String getBooksJson(long page);

    String getBookCustomJson(String id, long page);

    String getBookStandardJson(String id);

    String getSearchResultJson(String query, long page);

}
