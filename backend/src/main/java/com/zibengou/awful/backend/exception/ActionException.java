package com.zibengou.awful.backend.exception;

public class ActionException extends BusinessException {
    private static final String messageCode = "1008";

    private static final Integer statusCode = 400;

    public ActionException() {
        super(statusCode, messageCode);
    }

    public ActionException(String error) {
        super(statusCode, messageCode, error);
    }
}
