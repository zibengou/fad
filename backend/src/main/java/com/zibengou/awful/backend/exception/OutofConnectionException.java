package com.zibengou.awful.backend.exception;

public class OutofConnectionException extends BusinessException {
    private static final String messageCode = "1010";

    private static final Integer statusCode = 421;

    public OutofConnectionException() {
        super(statusCode, messageCode);
    }

    public OutofConnectionException(String error) {
        super(statusCode, messageCode, error);
    }
}
