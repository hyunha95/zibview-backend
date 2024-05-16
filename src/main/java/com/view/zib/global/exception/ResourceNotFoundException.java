package com.view.zib.global.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String datasource, Long id) {
        super("Resource not found in " + datasource + " with id: " + id);
    }

    public ResourceNotFoundException(String datasource, String subject) {
        super("Resource not found in " + datasource + " with subject: " + subject);
    }
}
