package com.zibengou.awful.backend.exception;

public class AuthorizationException extends BusinessException {
    private static final String messageCode = "1001";

    private static final Integer statusCode = 401;

    public AuthorizationException() {
        super(statusCode, messageCode);
    }

    public AuthorizationException(String error) {
        super(statusCode, messageCode, error);
    }
}
