package com.zibengou.awful.backend.exception;

public class ResourceException extends BusinessException {
    private static final String messageCode = "1002";

    private static final Integer statusCode = 400;

    public ResourceException() {
        super(statusCode, messageCode);
    }

    public ResourceException(String error) {
        super(statusCode, messageCode, error);
    }
}
