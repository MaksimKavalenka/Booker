package by.training.utility;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public abstract class Parser {

    public static String getErrorsMessagesFromObjectError(Errors errors) {
        StringBuilder errorsMessages = new StringBuilder();
        for (ObjectError error : errors.getAllErrors()) {
            errorsMessages.append(error.getDefaultMessage() + "; ");
        }
        return errorsMessages.toString();
    }

}
