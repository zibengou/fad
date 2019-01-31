package com.zibengou.awful.backend.exception;

public class ResourceNotExistsException extends BusinessException {
    private static final String messageCode = "1002";

    private static final Integer statusCode = 404;

    public ResourceNotExistsException() {
        super(statusCode, messageCode);
    }

    public ResourceNotExistsException(String error) {
        super(statusCode, messageCode, error);
    }
}
