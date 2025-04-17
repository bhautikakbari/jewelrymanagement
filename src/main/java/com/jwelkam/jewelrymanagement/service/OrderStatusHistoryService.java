package com.jwelkam.jewelrymanagement.service;

import com.jwelkam.jewelrymanagement.dto.response.OrderStatusHistoryResponse;

import java.util.List;

public interface OrderStatusHistoryService {
    List<OrderStatusHistoryResponse> getStatusHistoryByOrder(Long orderId);
    OrderStatusHistoryResponse getStatusHistoryById(Long id);
}