package com.codeAsh.inventory_service.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codeAsh.inventory_service.Service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStockOrNot(@RequestParam String skuCode,@RequestParam Integer quantity){
        boolean stockExists = inventService.isInStock(skuCode, quantity);
        return stockExists;
    }
}
