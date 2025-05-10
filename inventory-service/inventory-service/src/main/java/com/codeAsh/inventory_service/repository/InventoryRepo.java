package com.codeAsh.inventory_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codeAsh.inventory_service.models.Inventory;

public interface InventoryRepo  extends JpaRepository<Inventory, Long>{
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
}
