package com.zibengou.awful.backend.exception;

public abstract class BasicException extends Exception {
    private String error;
    private String messageCode;
    private Integer statusCode;

    public BasicException() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }



    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }
}
