package com.view.zib.global.exception;

import com.view.zib.global.exception.domain.Error;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import com.view.zib.global.exception.exceptions.SqlFailedException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Error> illegalStateExceptionHandler(IllegalStateException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error(e.getMessage(), LocalDateTime.now(), new ArrayList<>()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Error> maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error("File size is too large. Maximum upload size is " + maxFileSize, LocalDateTime.now(), new ArrayList<>()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new Error(e.getMessage(), LocalDateTime.now(), new ArrayList<>()));
    }

    /**
     * SQL 실행 중 실패한 경우
     */
    @ExceptionHandler(SqlFailedException.class)
    public ResponseEntity<Error> sqlFailedExceptionHandler(SqlFailedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new Error(e.getMessage(), LocalDateTime.now(), new ArrayList<>()));
    }

    /**
     * @Valid 유효성 검사 실패한 경우
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        var invalidInputs = new ArrayList<Error.InvalidInput>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            invalidInputs.add(new Error.InvalidInput(fieldError.getField(), fieldError.getField().getClass().getSimpleName(), fieldError.getDefaultMessage()));
        });

        invalidInputs.sort(Comparator.comparing(Error.InvalidInput::name));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error("Invalid input", LocalDateTime.now(), invalidInputs));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error("Invalid input", LocalDateTime.now(), new ArrayList<>()));
    }

    /**
     * @Valid 유효성 검사 실패한 경우
     * @param e
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Error> validationExceptionHandler(ValidationException e) {
        if (e instanceof ConstraintViolationException cve) {
            var invalidInputs = new ArrayList<Error.InvalidInput>();
            cve.getConstraintViolations().forEach(violation -> {
                invalidInputs.add(new Error.InvalidInput(violation.getPropertyPath().toString(), violation.getInvalidValue().getClass().getSimpleName(), violation.getMessage()));
            });
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Error("Invalid input", LocalDateTime.now(), invalidInputs));
        }

        return ResponseEntity.status(400).body(new Error(e.getMessage(), LocalDateTime.now(), new ArrayList<>()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error("Invalid input", LocalDateTime.now(), new ArrayList<>()));
    }

    /**
     * 그 외 예외 처리
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error("Internal server error", LocalDateTime.now(), new ArrayList<>()));
    }
}
