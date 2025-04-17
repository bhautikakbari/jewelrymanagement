package com.jwelkam.jewelrymanagement.controller;

import com.jwelkam.jewelrymanagement.dto.request.OrderRequest;
import com.jwelkam.jewelrymanagement.dto.request.OrderStatusUpdateRequest;
import com.jwelkam.jewelrymanagement.dto.response.OrderResponse;
import com.jwelkam.jewelrymanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderResponse> getOrderByOrderNumber(@PathVariable String orderNumber) {
        return ResponseEntity.ok(orderService.getOrderByOrderNumber(orderNumber));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }
    
    @GetMapping("/from-city/{cityId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByFromCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(orderService.getOrdersByFromCity(cityId));
    }
    
    @GetMapping("/to-city/{cityId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByToCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(orderService.getOrdersByToCity(cityId));
    }
    
    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByAssignedWorker(@PathVariable Long workerId) {
        return ResponseEntity.ok(orderService.getOrdersByAssignedWorker(workerId));
    }
    
    @PutMapping("/update-status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
         @RequestBody OrderStatusUpdateRequest statusUpdateRequest) {
        return ResponseEntity.ok(orderService.updateOrderStatus(statusUpdateRequest));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}