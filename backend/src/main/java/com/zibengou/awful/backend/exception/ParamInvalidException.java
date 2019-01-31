package com.zibengou.awful.backend.exception;

public class ParamInvalidException extends BusinessException {
    private static final String messageCode = "1000";

    public ParamInvalidException() {
        super(400, messageCode);
    }

    public ParamInvalidException(String error) {
        super(400, messageCode, error);
    }
}
