package com.example.inventory_service.util;

import com.example.inventory_service.entity.InventoryEntity;
import com.example.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;


    public DataLoader(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(inventoryRepository.count() == 0){
            inventoryRepository.save(new InventoryEntity(null,"PROD-001",100));
            inventoryRepository.save(new InventoryEntity(null,"PROD-002",0));
            System.out.println("Dummy data loaded!!!");

        }

    }
}
