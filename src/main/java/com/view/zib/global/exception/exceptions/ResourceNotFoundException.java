package com.view.zib.global.exception.exceptions;

import com.view.zib.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(String datasource, Long id) {
        super("Resource not found in " + datasource + " with id: " + id, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String datasource, String email) {
        super("Resource not found in " + datasource + " with email: " + email, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String datasource, String column, String value) {
        super(String.format("Resource not found in %s with %s: %s", datasource, column, value));
    }
}
