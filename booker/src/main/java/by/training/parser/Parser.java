package by.training.parser;

import static by.training.constants.ISO8601DateConstants.Adding.*;
import static by.training.constants.ISO8601DateConstants.Pattern.*;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import nl.siegmann.epublib.domain.Author;

public abstract class Parser {

    public static String getErrorsMessages(Errors errors) {
        StringBuilder errorsMessages = new StringBuilder();
        for (ObjectError error : errors.getAllErrors()) {
            errorsMessages.append(error.getDefaultMessage() + "; ");
        }
        return errorsMessages.toString();
    }

    public static Date parseToIso8601(String input) throws ParseException {

        if (YYYY_PATTERN.matcher(input).matches()) {
            input += YYYY_ADDING;
        } else if (YYYY_MM_PATTERN.matcher(input).matches()) {
            input += YYYY_MM_ADDING;
        } else if (YYYY_MM_DD_PATTERN.matcher(input).matches()) {
            input += YYYY_MM_DD_ADDING;
        } else if (YYYY_MM_DD_HH_MM_PATTERN.matcher(input).matches()) {
            input += YYYY_MM_DD_HH_MM_ADDING;
            input = input.replace(" ", "T");
        } else if ((YYYY_MM_DD_HH_MM_SS_PATTERN.matcher(input).matches())
                | (YYYY_MM_DD_HH_MM_SS_MM_PATTERN.matcher(input).matches())) {
            input += YYYY_MM_DD_HH_MM_SS_ADDING;
            input = input.replace(" ", "T");
        } else if (YYYY_MM_DD_HH_MM_SS_MM_GMT_PATTERN.matcher(input).matches()) {
            input = input.replace(" ", "T");
        }

        return java.util.Date.from(ZonedDateTime.parse(input).toInstant());
    }

    public static String parseAuthor(Author author) {
        return author.getFirstname() + " " + author.getLastname();
    }

}
