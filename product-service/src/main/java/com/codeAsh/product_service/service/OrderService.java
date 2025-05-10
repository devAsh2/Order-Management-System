package com.codeAsh.product_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class OrderService {
    
    private final WebClient webClient;
    
    @Value("${order.service.url}")
    private String orderServiceUrl;
    
    public OrderService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(orderServiceUrl).build();
    }
    
    public Mono<String> placeOrder(String skuCode, int quantity) {
        return webClient.post()
                .uri("/api/order")
                .bodyValue(new OrderRequest(skuCode, quantity))
                .retrieve()
                .bodyToMono(String.class);
    }
    
    private record OrderRequest(String skuCode, int quantity) {}
} 