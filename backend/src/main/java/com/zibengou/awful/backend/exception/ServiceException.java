package com.zibengou.awful.backend.exception;

public class ServiceException extends BusinessException {
    private static final String messageCode = "1011";

    private static final Integer statusCode = 400;

    public ServiceException() {
        super(statusCode, messageCode);
    }

    public ServiceException(String error) {
        super(statusCode, messageCode, error);
    }
}
