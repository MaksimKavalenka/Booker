package by.training.service.dao;

public interface BookServiceDAO {

    String getBooksJson(long page);

    String getBookJson(String metadataId, long page);

}
