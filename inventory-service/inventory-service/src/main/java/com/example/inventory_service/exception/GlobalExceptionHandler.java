package com.example.inventory_service.exception;

import com.example.inventory_service.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFound(ProductNotFoundException ex, HttpServletRequest request){
        ErrorResponseDto dto = new ErrorResponseDto();
        dto.setApiPath(request.getRequestURI());
        dto.setErrorCode(HttpStatus.NOT_FOUND.value());
        dto.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }

}
