package by.training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.training.entity.BookStatusEntity;
import by.training.jpa.repository.BookStatusRepository;
import by.training.service.dao.BookStatusServiceDAO;

@Service("bookStatusService")
public class BookStatusService implements BookStatusServiceDAO {

    @Autowired
    private BookStatusRepository repository;

    @Override
    public BookStatusEntity createBookStatus(BookStatusEntity bookStatus) {
        return repository.save(bookStatus);
    }

    @Override
    public void deleteUnsuccessfulBookStatuses(long uploaderId) {
        repository.deleteByUploader_IdAndStatusFalse(uploaderId);
    }

    @Override
    public List<BookStatusEntity> getSuccessfulBookStatuses(long uploaderId) {
        return repository.findByUploader_IdAndStatusTrue(uploaderId);
    }

    @Override
    public List<BookStatusEntity> getUnsuccessfulBookStatuses(long uploaderId) {
        return repository.findByUploader_IdAndStatusFalse(uploaderId);
    }

    @Override
    public boolean checkBookStatuses(String id) {
        return repository.checkBookStatus(id);
    }

}
