package com.zibengou.awful.backend.exception;

public class DataBaseException extends BusinessException {
    private static final String messageCode = "1013";

    public DataBaseException() {
        super(400, messageCode);
    }

    public DataBaseException(String error) {
        super(400, messageCode, error);
    }
}
