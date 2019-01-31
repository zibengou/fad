package com.zibengou.awful.backend.exception;

public class BusinessException extends BasicException {

    public BusinessException() {
    }

    public BusinessException(Integer statusCode) {
        this.setStatusCode(statusCode);
    }

    public BusinessException(Integer statusCode, String messageCode) {
        this.setStatusCode(statusCode);
        this.setMessageCode(messageCode);
    }

    public BusinessException(Integer statusCode, String messageCode, String error) {
        this.setStatusCode(statusCode);
        this.setMessageCode(messageCode);
        this.setError(error);
    }


}
