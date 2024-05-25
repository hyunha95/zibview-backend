package com.view.zib.global.exception.domain;

import java.time.LocalDateTime;
import java.util.List;

// add code when it is needed
public record Error(String message, LocalDateTime timestamp, List<InvalidInput> invalidInputs) {

    public record InvalidInput(String name, String type, String message) {}
}
