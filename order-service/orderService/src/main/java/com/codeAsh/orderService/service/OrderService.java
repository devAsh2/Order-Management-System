package com.codeAsh.orderService.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.codeAsh.orderService.client.InventoryClient;
import com.codeAsh.orderService.dto.OrderLineItemsDTO;
import com.codeAsh.orderService.dto.OrderRequest;
import com.codeAsh.orderService.models.OrderLineItems;
import com.codeAsh.orderService.models.orders;
import com.codeAsh.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public orders placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(
            orderRequest.getOrderLineItemsDtoList().get(0).getSkuCode(), 
            orderRequest.getOrderLineItemsDtoList().get(0).getQuantity()
        );

        if(isProductInStock) {
            orders order = new orders();
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDTO)
                .toList();
            order.setOrderLineItemsList(orderLineItems);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Product with SkuCode " + 
                orderRequest.getOrderLineItemsDtoList().get(0).getSkuCode() + " is not in stock");
        }
    }

    public orders getOrder(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
