package com.example.inventory_service.service;

import com.example.inventory_service.entity.InventoryEntity;
import com.example.inventory_service.exception.ProductNotFoundException;
import com.example.inventory_service.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isProductInStock(String productId){
        log.info("Inside isProductInStock() method of InventoryService.......");
        InventoryEntity inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product with id: "+productId+" not found"));
        return inventory.getAvailableQuantity()>0;
    }
}
