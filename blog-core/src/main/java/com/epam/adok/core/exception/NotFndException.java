package com.epam.adok.core.exception;

public class NotFndException extends RuntimeException {

    public NotFndException(String message) {
        super(message);
    }

    public NotFndException(String message, Throwable cause) {
        super(message, cause);
    }
}
