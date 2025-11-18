package com.example.product_service.dto;

import java.math.BigDecimal;

public record ProductRequest(Long id, String name, String description, BigDecimal price) {
}
