package com.app.exceptions;

public class NotRetryableException extends RuntimeException {
    public NotRetryableException(String message) {
        super(message);
    }

    public NotRetryableException(Exception ex) {
        super(ex);
    }
}
