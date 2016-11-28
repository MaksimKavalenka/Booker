package by.training.utility;

import static by.training.constants.MessageConstants.UPLOAD_FILE_ERROR_MESSAGE;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import by.training.exception.UploadException;

public abstract class Utility {

    public static void uploadFile(InputStream in, String path) throws UploadException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path))) {
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (FileNotFoundException e) {
            throw new UploadException(UPLOAD_FILE_ERROR_MESSAGE);
        } catch (IOException e) {
            throw new UploadException(e.getMessage());
        }
    }

    public static void deleteFolder(String path) {
        File folder = new File(path);
        String[] entries = folder.list();
        for (String entry : entries) {
            File currentFile = new File(folder.getPath(), entry);
            currentFile.delete();
        }
        folder.delete();
    }

}
