package by.training.utility;

import static by.training.constants.MessageConstants.UPLOAD_FILE_ERROR_MESSAGE;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import by.training.exception.ValidationException;

public abstract class Utility {

    public static void uploadFile(InputStream in, String path) throws ValidationException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path))) {
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (FileNotFoundException e) {
            throw new ValidationException(UPLOAD_FILE_ERROR_MESSAGE);
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
