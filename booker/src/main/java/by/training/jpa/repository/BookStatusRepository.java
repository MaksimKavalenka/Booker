package by.training.jpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import by.training.entity.BookStatusEntity;

public interface BookStatusRepository extends CrudRepository<BookStatusEntity, Long> {

    List<BookStatusEntity> findByUploader_IdAndStatusTrue(long uploaderId);

    List<BookStatusEntity> findByUploader_IdAndStatusFalse(long uploaderId);

}
