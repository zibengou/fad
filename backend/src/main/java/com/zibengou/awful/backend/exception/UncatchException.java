package com.zibengou.awful.backend.exception;

public class UncatchException extends BusinessException {
    private static final String messageCode = "1005";

    private static final Integer statusCode = 500;

    public UncatchException() {
        super(statusCode, messageCode);
    }

    public UncatchException(String error) {
        super(statusCode, messageCode, error);
    }
}
