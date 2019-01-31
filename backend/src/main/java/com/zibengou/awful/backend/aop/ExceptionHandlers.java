package com.zibengou.awful.backend.aop;

import com.zibengou.awful.backend.exception.BusinessException;
import com.zibengou.awful.backend.exception.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Locale;


@RestControllerAdvice
public class ExceptionHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);


    @Autowired
    MessageSource handler;

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<ExceptionResponse> unCatchHandler(UndeclaredThrowableException ex) {
        Throwable e = ex.getUndeclaredThrowable();
        if (e instanceof BusinessException) {
            String message = getString(((BusinessException) e).getMessageCode());
            return new ResponseEntity<>(new ExceptionResponse(message, ((BusinessException) e).getError()), HttpStatus.valueOf(((BusinessException) e).getStatusCode()));
        } else {
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), "未捕获异常"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> businessHandler(BusinessException e, ServerHttpRequest request) {
        String message = getString(e.getMessageCode());
        return new ResponseEntity<>(new ExceptionResponse(message, e.getError()), HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse unCatchHandler(Exception ex) {
        return new ExceptionResponse(ex.getMessage(), "未捕获异常");
    }

    private String getString(String key) {
        return this.handler.getMessage(key, null, Locale.SIMPLIFIED_CHINESE);
    }
}

