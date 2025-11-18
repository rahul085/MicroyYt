package com.example.order_service.service;

import com.example.order_service.dto.ErrorResponseDto;
import com.example.order_service.dto.OrderRequestDto;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.exception.ProductNotFoundException;
import com.example.order_service.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.client.HttpClientErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String INVENTORY_URL = "http://inventory-service/api/inventory/";
    private static final String SERVICE_NAME = "inventory-service";

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "inventoryFallback")
    @Retry(name = SERVICE_NAME)
    public OrderEntity placeOrder(OrderRequestDto dto) {

        log.info("Inside placeOrder() of OrderService............");

        // 1. Construct the url
        String url = INVENTORY_URL + dto.getProductId();
        // 2.Call Inventory service (synchronous call)
        try {
            Boolean isInStock = restTemplate.getForObject(url, Boolean.class);
        // 3. create Order Entity
            OrderEntity order = new OrderEntity();
            order.setProductId(dto.getProductId());
            order.setQuantity(dto.getQuantity());
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("CREATED");
            order.setPrice(100);
        // 4. Decide status based on inventory response
            if (Boolean.TRUE.equals(isInStock)) {
                order.setStatus("CONFIRMED");
            } else {
                order.setStatus("OUT_OF_STOCK");
            }
            return orderRepository.save(order);
        } catch (HttpClientErrorException ex) {
            try {
                String jsonResponse = ex.getResponseBodyAsString();
                ErrorResponseDto errorResponseDto = objectMapper.readValue(jsonResponse, ErrorResponseDto.class);
                throw new ProductNotFoundException(errorResponseDto.getErrorMessage());
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public OrderEntity inventoryFallback(OrderRequestDto dto, Throwable t) {
// PRINT THIS TO KNOW THE EXACT EXCEPTION
        System.out.println("EXCEPTION CLASS: " + t.getClass().getName());
        if (t instanceof ProductNotFoundException) {
            throw (ProductNotFoundException) t;
        }
        log.error("----FALLBACK TRIGGERED !! Reason: {}", t.getMessage() + "----");
        OrderEntity order = new OrderEntity();
        order.setProductId(dto.getProductId());
        order.setQuantity(dto.getQuantity());
        order.setOrderDate(LocalDateTime.now());
        order.setPrice(100.0);

        // Set a special status so Admin can check later
        order.setStatus("PENDING_VERIFICATION");

        return orderRepository.save(order);

    }
}

