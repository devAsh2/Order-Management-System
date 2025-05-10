package com.codeAsh.product_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class InventoryService {
    
    private final WebClient webClient;
    
    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;
    
    public InventoryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(inventoryServiceUrl).build();
    }
    
    public Mono<Boolean> isInStock(String skuCode) {
        return webClient.get()
                .uri("/api/inventory/{skuCode}", skuCode)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
} 