package com.codeAsh.inventory_service.Service;

import org.springframework.stereotype.Service;

import com.codeAsh.inventory_service.repository.InventoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepository;


    public boolean isInStock(String skuCode, Integer quantity){
        boolean inStock = inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
        return inStock;
    }
}
