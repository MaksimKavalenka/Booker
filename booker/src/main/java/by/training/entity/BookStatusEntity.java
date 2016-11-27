package by.training.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book_status")
public class BookStatusEntity extends AbstractEntity {

    private static final long serialVersionUID = -3489247654966871219L;

    @Column(name = "fileName", nullable = false, length = 255)
    private String            fileName;

    @Column(name = "status", nullable = false)
    private boolean           status;

    @Column(name = "message", length = 255)
    private String            message;

    @ManyToOne(targetEntity = UserEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private UserEntity        uploader;

    public BookStatusEntity() {
        super();
    }

    public BookStatusEntity(String fileName, boolean status, UserEntity uploader) {
        super();
        this.fileName = fileName;
        this.status = status;
        this.uploader = uploader;
    }

    public BookStatusEntity(String fileName, boolean status, String message, UserEntity uploader) {
        super();
        this.fileName = fileName;
        this.status = status;
        this.message = message;
        this.uploader = uploader;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserEntity getUploader() {
        return uploader;
    }

    public void setUploader(UserEntity uploader) {
        this.uploader = uploader;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",fileName:" + fileName + ",status:"
                + status + ",message:" + message + "]";
    }

}
