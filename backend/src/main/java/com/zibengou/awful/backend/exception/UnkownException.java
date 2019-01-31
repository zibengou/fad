package com.zibengou.awful.backend.exception;

public class UnkownException extends BusinessException {
    private static final String messageCode = "1006";

    private static final Integer statusCode = 500;

    public UnkownException() {
        super(statusCode, messageCode);
    }

    public UnkownException(String error) {
        super(statusCode, messageCode, error);
    }
}
