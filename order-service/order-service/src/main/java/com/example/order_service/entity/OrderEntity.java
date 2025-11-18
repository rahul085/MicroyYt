package com.example.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "ORDERS")
public class OrderEntity {
    @Id
    @SequenceGenerator(name = "OrdSeq",allocationSize = 1,initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "OrdSeq")
    private Long id;
    private String productId;
    private Integer quantity;
    private double price;
    private String status;
    private LocalDateTime orderDate;
}
