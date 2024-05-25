package com.view.zib.global.exception.exceptions;

import com.view.zib.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends CustomException {
    public InvalidAccessTokenException() {
        super("Invalid access token", HttpStatus.UNAUTHORIZED);
    }
}
