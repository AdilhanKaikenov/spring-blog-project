package com.epam.adok.core.exception;

public class DateParsingException extends Exception {

    public DateParsingException(String message) {
        super(message);
    }

    public DateParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateParsingException(Throwable cause) {
        super(cause);
    }
}
