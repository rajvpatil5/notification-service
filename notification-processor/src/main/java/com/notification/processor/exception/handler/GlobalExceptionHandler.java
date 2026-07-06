package com.notification.processor.exception.handler;

import com.notification.processor.exception.AbstractException;
import com.notification.processor.exception.ResourceNotFoundException;
import com.notification.processor.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.Supplier;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException exception) {
        return genericExceptionHandler(exception, () -> ResponseEntity.badRequest().body(exception.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return genericExceptionHandler(exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    private ResponseEntity<String> genericExceptionHandler(final AbstractException exception,
                                                           Supplier<ResponseEntity<String>> runner) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getErrorMessage());
    }

}
