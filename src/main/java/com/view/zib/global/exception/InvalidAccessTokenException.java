package com.view.zib.global.exception;

public class InvalidAccessTokenException extends RuntimeException {
    public InvalidAccessTokenException() {
        super("Invalid access token");
    }
}
