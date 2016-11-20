package by.training.solr.uploader;

import by.training.exception.UploadException;

public interface SolrUploadable {

    int getProgress();

    void upload(String directoryPath, String fileName, String id, String uploader)
            throws UploadException;

}
