package com.view.zib.global.exception.exceptions;

import com.view.zib.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StorageFileNotFoundException extends CustomException {
    public StorageFileNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
