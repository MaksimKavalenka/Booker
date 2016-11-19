package by.training.uploader;

import by.training.exception.ValidationException;
import nl.siegmann.epublib.domain.Book;

public interface Uploadable {

    int getProgress();

    void uploadBook(Book book, String id, String uploader, String path) throws ValidationException;

    void uploadMetadata(Book book, String id, String uploader) throws ValidationException;

    void uploadContent(String metadataId, String path) throws ValidationException;

}
