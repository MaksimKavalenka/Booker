package by.training.service.dao;

import java.util.List;

import by.training.entity.BookStatusEntity;

public interface BookStatusServiceDAO {

    BookStatusEntity createBookStatus(BookStatusEntity bookStatus);

    List<BookStatusEntity> getSuccessfulBookStatuses(long uploaderId);

    List<BookStatusEntity> getUnsuccessfulBookStatuses(long uploaderId);

}
