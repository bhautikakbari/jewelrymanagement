package com.jwelkam.jewelrymanagement.service;

import com.jwelkam.jewelrymanagement.dto.request.OrderRequest;
import com.jwelkam.jewelrymanagement.dto.request.OrderStatusUpdateRequest;
import com.jwelkam.jewelrymanagement.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long id);
    OrderResponse getOrderByOrderNumber(String orderNumber);
    List<OrderResponse> getAllOrders();
    List<OrderResponse> getOrdersByStatus(String status);
    List<OrderResponse> getOrdersByFromCity(Long cityId);
    List<OrderResponse> getOrdersByToCity(Long cityId);
    List<OrderResponse> getOrdersByAssignedWorker(Long workerId);
  OrderResponse updateOrderStatus(OrderStatusUpdateRequest statusUpdateRequest);
    void deleteOrder(Long id);
}