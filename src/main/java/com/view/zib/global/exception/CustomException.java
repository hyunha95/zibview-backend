package com.view.zib.global.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
