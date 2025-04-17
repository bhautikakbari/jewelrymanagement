package com.jwelkam.jewelrymanagement.controller;

import com.jwelkam.jewelrymanagement.dto.response.OrderStatusHistoryResponse;
import com.jwelkam.jewelrymanagement.service.OrderStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-status-history")
public class OrderStatusHistoryController {
    
    private final OrderStatusHistoryService orderStatusHistoryService;
    
    @Autowired
    public OrderStatusHistoryController(OrderStatusHistoryService orderStatusHistoryService) {
        this.orderStatusHistoryService = orderStatusHistoryService;
    }
    
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderStatusHistoryResponse>> getStatusHistoryByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderStatusHistoryService.getStatusHistoryByOrder(orderId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderStatusHistoryResponse> getStatusHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(orderStatusHistoryService.getStatusHistoryById(id));
    }
}