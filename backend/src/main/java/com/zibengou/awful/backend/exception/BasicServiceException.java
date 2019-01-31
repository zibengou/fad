package com.zibengou.awful.backend.exception;

public class BasicServiceException extends Exception {
    private String error;
    private String msg;
    private Integer statusCode;
    private String serviceType;

    public BasicServiceException() {
    }

    public BasicServiceException(Integer statusCode, ExceptionResponse response) {
        this.statusCode = statusCode;
        this.msg = response.getMessage();
        this.error = response.getError();
    }

    public BasicServiceException(Integer statusCode, String message, String error) {
        this.error = error;
        this.msg = message;
        this.statusCode = statusCode;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
