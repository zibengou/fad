package com.zibengou.awful.backend.exception;

public class ServiceLogicException extends BusinessException {
    private static final String messageCode = "1004";

    private static final Integer statusCode = 400;

    public ServiceLogicException() {
        super(statusCode, messageCode);
    }

    public ServiceLogicException(String error) {
        super(statusCode, messageCode, error);
    }
}
