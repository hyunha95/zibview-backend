package com.view.zib.global.exception.exceptions;

import com.view.zib.global.exception.CustomException;

public class SqlFailedException extends CustomException {

    public SqlFailedException(String message) {
        super(message);
    }
}
