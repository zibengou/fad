package com.zibengou.awful.backend.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends BasicException {

    public static HttpStatus httpStatus = HttpStatus.valueOf(400);

    public HttpException() {
        this.setMessageCode("0000");
    }


    public HttpException(Integer statusCode) {
        this.setMessageCode("0000");
        this.setStatusCode(statusCode);
    }

    public HttpException(Integer statusCode, String messageCode) {
        this.setStatusCode(statusCode);
        this.setMessageCode(messageCode);
    }

    public HttpException(Integer statusCode, String messageCode, String error) {
        this.setStatusCode(statusCode);
        this.setMessageCode(messageCode);
        this.setError(error);
    }
}
