package com.itmo.simaland.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class SqlExceptionHandler {


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleNotFoundException(DataIntegrityViolationException exception) {
        String message = exception.getMessage().contains("unique_username_constraint") ? "Username already exist" : "Unexpected exception: " + exception.getMessage();

        return new ResponseEntity<>(Map.of("error", message), HttpStatus.CONFLICT);
    }

}
