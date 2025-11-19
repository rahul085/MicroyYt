package com.example.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object >> handleUserNotFound(UserNotFoundException ex){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("Status", HttpStatus.NOT_FOUND);
        errorResponse.put("Message",ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String,Object >> handleRoleNotFound(RoleNotFoundException ex){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("Status", HttpStatus.NOT_FOUND);
        errorResponse.put("Message",ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String,Object >> handleInvalidToken(InvalidTokenException ex){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("Status", HttpStatus.BAD_REQUEST);
        errorResponse.put("Message",ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
