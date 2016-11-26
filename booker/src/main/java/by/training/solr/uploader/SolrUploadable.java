package by.training.solr.uploader;

import by.training.exception.UploadException;

@FunctionalInterface
public interface SolrUploadable {

    void upload(String directoryPath, String fileName, String id, String uploader)
            throws UploadException;

}
