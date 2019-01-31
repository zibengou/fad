package com.zibengou.awful.backend.exception;

public class AuthenticationException extends BusinessException {
    private static final String messageCode = "1007";

    private static final Integer statusCode = 400;

    public AuthenticationException() {
        super(statusCode, messageCode);
    }

    public AuthenticationException(String error) {
        super(statusCode, messageCode, error);
    }
}
