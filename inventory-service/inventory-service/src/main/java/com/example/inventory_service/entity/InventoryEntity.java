package com.example.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INVENTORY")
public class InventoryEntity {

    @Id
    @SequenceGenerator(name = "InvSeq",allocationSize = 1,initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "InvSeq")
    private Long id;
    @Column(unique = true,nullable = false)
    private String productId;
    private Integer availableQuantity;
}
