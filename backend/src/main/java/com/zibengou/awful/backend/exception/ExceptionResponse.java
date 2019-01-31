package com.zibengou.awful.backend.exception;

public class ExceptionResponse {
    private String message;
    private String error;

    public ExceptionResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public ExceptionResponse() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
