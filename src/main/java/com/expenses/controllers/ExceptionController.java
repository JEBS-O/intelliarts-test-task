package com.expenses.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolationExceptionHandler(ConstraintViolationException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity nullPointerExceptionHandler(NullPointerException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
