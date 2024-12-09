package com.orlov.SpringBootSecurityRest.controller;

import com.orlov.SpringBootSecurityRest.util.UserErrorResponse;
import com.orlov.SpringBootSecurityRest.util.UserNotCreateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<UserErrorResponse> handleException(EntityNotFoundException exception) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(exception.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<UserErrorResponse> handleException(EntityExistsException exception) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(exception.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(UserNotCreateException.class)
    public ResponseEntity<UserErrorResponse> handleException(UserNotCreateException exception) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(exception.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
