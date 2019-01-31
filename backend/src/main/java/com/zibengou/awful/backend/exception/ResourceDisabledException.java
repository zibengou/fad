package com.zibengou.awful.backend.exception;

public class ResourceDisabledException extends BusinessException {
    private static final String messageCode = "1009";

    private static final Integer statusCode = 410;

    public ResourceDisabledException() {
        super(statusCode, messageCode);
    }

    public ResourceDisabledException(String error) {
        super(statusCode, messageCode, error);
    }
}
