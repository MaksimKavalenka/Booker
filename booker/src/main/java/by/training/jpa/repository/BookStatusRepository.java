package by.training.jpa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.training.entity.BookStatusEntity;

public interface BookStatusRepository extends CrudRepository<BookStatusEntity, Long> {

    @Transactional
    void deleteByUploader_IdAndStatusFalse(long uploaderId);

    List<BookStatusEntity> findByUploader_IdAndStatusTrue(long uploaderId);

    List<BookStatusEntity> findByUploader_IdAndStatusFalse(long uploaderId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM BookStatusEntity WHERE id = ?1")
    boolean checkBookStatus(String id);

}
