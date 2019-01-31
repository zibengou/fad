package com.zibengou.awful.backend.exception;

public class OutofLimitException extends BusinessException {
    private static final String messageCode = "1003";

    private static final Integer statusCode = 400;

    public OutofLimitException() {
        super(statusCode, messageCode);
    }

    public OutofLimitException(String error) {
        super(statusCode, messageCode, error);
    }
}
