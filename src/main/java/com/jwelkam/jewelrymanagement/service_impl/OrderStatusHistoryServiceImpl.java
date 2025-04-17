package com.jwelkam.jewelrymanagement.service_impl;

import com.jwelkam.jewelrymanagement.dto.response.AdminResponse;
import com.jwelkam.jewelrymanagement.dto.response.OrderStatusHistoryResponse;
import com.jwelkam.jewelrymanagement.entity.Order;
import com.jwelkam.jewelrymanagement.entity.OrderStatusHistory;
import com.jwelkam.jewelrymanagement.exception.ResourceNotFoundException;
import com.jwelkam.jewelrymanagement.repository.OrderRepository;
import com.jwelkam.jewelrymanagement.repository.OrderStatusHistoryRepository;
import com.jwelkam.jewelrymanagement.service.OrderStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryService {
    
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderRepository orderRepository;
    
    @Autowired
    public OrderStatusHistoryServiceImpl(
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            OrderRepository orderRepository) {
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderRepository = orderRepository;
    }
    
    @Override
    public List<OrderStatusHistoryResponse> getStatusHistoryByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        
        List<OrderStatusHistory> statusHistories = orderStatusHistoryRepository.findByOrderOrderByStatusDateDesc(order);
        
        return statusHistories.stream()
                .map(this::mapToOrderStatusHistoryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public OrderStatusHistoryResponse getStatusHistoryById(Long id) {
        OrderStatusHistory statusHistory = orderStatusHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatusHistory", "id", id));
        
        return mapToOrderStatusHistoryResponse(statusHistory);
    }
    
    private OrderStatusHistoryResponse mapToOrderStatusHistoryResponse(OrderStatusHistory statusHistory) {
        OrderStatusHistoryResponse response = new OrderStatusHistoryResponse();
        response.setId(statusHistory.getId());
        response.setOrderId(statusHistory.getOrder().getId());
        response.setStatus(statusHistory.getStatus());
        response.setStatusDate(statusHistory.getStatusDate());
        response.setComments(statusHistory.getComments());
        
        if (statusHistory.getUpdatedBy() != null) {
            AdminResponse adminResponse = new AdminResponse();
            adminResponse.setId(statusHistory.getUpdatedBy().getId());
            adminResponse.setName(statusHistory.getUpdatedBy().getName());
            adminResponse.setEmail(statusHistory.getUpdatedBy().getEmail());
            adminResponse.setMobileNumber(statusHistory.getUpdatedBy().getMobileNumber());
            response.setUpdatedBy(adminResponse);
        }
        
        return response;
    }
}