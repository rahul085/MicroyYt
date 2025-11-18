package com.example.order_service.exception;

import com.example.order_service.repository.OrderRepository;

public class OrderProcessingException extends RuntimeException{
    public OrderProcessingException(String message) {
        super(message);
    }
}
