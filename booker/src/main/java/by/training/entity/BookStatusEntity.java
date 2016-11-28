package by.training.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import by.training.bean.BookStatusPrimaryKey;

@Entity
@Table(name = "book_status")
@IdClass(BookStatusPrimaryKey.class)
public class BookStatusEntity implements Serializable {

    private static final long serialVersionUID = -3489247654966871219L;

    @Id
    private String            id;

    @Id
    private long              uploaderId;

    @Column(name = "fileName", nullable = false, length = 255)
    private String            fileName;

    @Column(name = "status", nullable = false)
    private boolean           status;

    @Column(name = "message", length = 255)
    private String            message;

    @Id
    @ManyToOne(targetEntity = UserEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private UserEntity        uploader;

    public BookStatusEntity() {
    }

    public BookStatusEntity(String id, String fileName, boolean status, String message,
            UserEntity uploader) {
        this.id = id;
        uploaderId = uploader.getId();
        this.fileName = fileName;
        this.status = status;
        this.message = message;
        this.uploader = uploader;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BookStatusEntity other = (BookStatusEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
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

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + id + ",fileName:" + fileName + ",status:" + status
                + ",message:" + message + "]";
    }

}
