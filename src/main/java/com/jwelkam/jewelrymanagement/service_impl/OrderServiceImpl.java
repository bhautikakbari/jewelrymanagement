package com.jwelkam.jewelrymanagement.service_impl;


import com.jwelkam.jewelrymanagement.dto.request.OrderRequest;
import com.jwelkam.jewelrymanagement.dto.request.OrderStatusUpdateRequest;
import com.jwelkam.jewelrymanagement.dto.response.*;
import com.jwelkam.jewelrymanagement.entity.*;
import com.jwelkam.jewelrymanagement.exception.ResourceNotFoundException;
import com.jwelkam.jewelrymanagement.repository.*;
import com.jwelkam.jewelrymanagement.service.OrderService;
import com.jwelkam.jewelrymanagement.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final CityRepository cityRepository;
    private final WorkerRepository workerRepository;
    private final AdminRepository adminRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    
    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            CityRepository cityRepository,
            WorkerRepository workerRepository,
            AdminRepository adminRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository) {
        this.orderRepository = orderRepository;
        this.cityRepository = cityRepository;
        this.workerRepository = workerRepository;
        this.adminRepository = adminRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
    }
    
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Get from city
        City fromCity = cityRepository.findById(orderRequest.getFromCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", orderRequest.getFromCityId()));
        
        // Get to city
        City toCity = cityRepository.findById(orderRequest.getToCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", orderRequest.getToCityId()));
        
        // Get assigned worker
        Worker assignedWorker = workerRepository.findById(orderRequest.getAssignedWorkerId())
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", orderRequest.getAssignedWorkerId()));
        
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setDescription(orderRequest.getDescription());
        order.setAmount(orderRequest.getAmount());
        order.setCreatedDate(new Date());
        order.setStatus(OrderStatus.CREATED.name());
        order.setFromCity(fromCity);
        order.setToCity(toCity);
        order.setAssignedWorker(assignedWorker);
        
        Order savedOrder = orderRepository.save(order);
        
        // Create initial status history
        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setOrder(savedOrder);
        statusHistory.setStatus(OrderStatus.CREATED.name());
        statusHistory.setStatusDate(new Date());
        statusHistory.setComments("Order created");
        
        orderStatusHistoryRepository.save(statusHistory);
        
        return mapToOrderResponse(savedOrder);
    }
    
    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        
        return mapToOrderResponse(order);
    }
    
    @Override
    public OrderResponse getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderNumber", orderNumber));
        
        return mapToOrderResponse(order);
    }
    
    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderResponse> getOrdersByStatus(String status) {
        List<Order> orders = orderRepository.findByStatus(status);
        
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderResponse> getOrdersByFromCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", cityId));
        
        List<Order> orders = orderRepository.findByFromCity(city);
        
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderResponse> getOrdersByToCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", cityId));
        
        List<Order> orders = orderRepository.findByToCity(city);
        
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderResponse> getOrdersByAssignedWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", workerId));
        
        List<Order> orders = orderRepository.findByAssignedWorker(worker);
        
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public OrderResponse updateOrderStatus(OrderStatusUpdateRequest statusUpdateRequest) {
        Order order = orderRepository.findById(statusUpdateRequest.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", statusUpdateRequest.getOrderId()));
        
        Admin updatedBy = adminRepository.findById(statusUpdateRequest.getUpdatedById())
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "id", statusUpdateRequest.getUpdatedById()));
        
        // Update order status
        order.setStatus(statusUpdateRequest.getStatus());
        Order updatedOrder = orderRepository.save(order);
        
        // Create status history
        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setOrder(updatedOrder);
        statusHistory.setStatus(statusUpdateRequest.getStatus());
        statusHistory.setStatusDate(new Date());
        statusHistory.setComments(statusUpdateRequest.getComments());
        statusHistory.setUpdatedBy(updatedBy);
        
        orderStatusHistoryRepository.save(statusHistory);
        
        return mapToOrderResponse(updatedOrder);
    }
    
    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        
        orderRepository.delete(order);
    }
    
    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setDescription(order.getDescription());
        response.setAmount(order.getAmount());
        response.setCreatedDate(order.getCreatedDate());
        response.setStatus(order.getStatus());
        
        // Map from city
        CityResponse fromCityResponse = new CityResponse();
        fromCityResponse.setId(order.getFromCity().getId());
        fromCityResponse.setName(order.getFromCity().getName());
        fromCityResponse.setState(order.getFromCity().getState());
        fromCityResponse.setCountry(order.getFromCity().getCountry());
        response.setFromCity(fromCityResponse);
        
        // Map to city
        CityResponse toCityResponse = new CityResponse();
        toCityResponse.setId(order.getToCity().getId());
        toCityResponse.setName(order.getToCity().getName());
        toCityResponse.setState(order.getToCity().getState());
        toCityResponse.setCountry(order.getToCity().getCountry());
        response.setToCity(toCityResponse);
        
        // Map assigned worker
        WorkerResponse workerResponse = new WorkerResponse();
        workerResponse.setId(order.getAssignedWorker().getId());
        workerResponse.setName(order.getAssignedWorker().getName());
        workerResponse.setEmail(order.getAssignedWorker().getEmail());
        workerResponse.setMobileNumber(order.getAssignedWorker().getMobileNumber());
        workerResponse.setAddress(order.getAssignedWorker().getAddress());
        workerResponse.setPhotoPath(order.getAssignedWorker().getPhotoPath());
        
        CityResponse workerCityResponse = new CityResponse();
        workerCityResponse.setId(order.getAssignedWorker().getCity().getId());
        workerCityResponse.setName(order.getAssignedWorker().getCity().getName());
        workerCityResponse.setState(order.getAssignedWorker().getCity().getState());
        workerCityResponse.setCountry(order.getAssignedWorker().getCity().getCountry());
        workerResponse.setCity(workerCityResponse);
        
        response.setAssignedWorker(workerResponse);
        
        // Map payments
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        if (order.getPayments() != null) {
            paymentResponses = order.getPayments().stream()
                    .map(payment -> {
                        PaymentResponse paymentResponse = new PaymentResponse();
                        paymentResponse.setId(payment.getId());
                        paymentResponse.setOrderId(payment.getOrder().getId());
                        paymentResponse.setAmount(payment.getAmount());
                        paymentResponse.setStatus(payment.getStatus());
                        paymentResponse.setPaymentDate(payment.getPaymentDate());
                        paymentResponse.setPaymentMethod(payment.getPaymentMethod());
                        return paymentResponse;
                    })
                    .collect(Collectors.toList());
        }
        response.setPayments(paymentResponses);
        
        // Map status history
        List<OrderStatusHistoryResponse> statusHistoryResponses = new ArrayList<>();
        if (order.getStatusHistory() != null) {
            statusHistoryResponses = order.getStatusHistory().stream()
                    .map(history -> {
                        OrderStatusHistoryResponse historyResponse = new OrderStatusHistoryResponse();
                        historyResponse.setId(history.getId());
                        historyResponse.setOrderId(history.getOrder().getId());
                        historyResponse.setStatus(history.getStatus());
                        historyResponse.setStatusDate(history.getStatusDate());
                        historyResponse.setComments(history.getComments());
                        
                        if (history.getUpdatedBy() != null) {
                            AdminResponse adminResponse = new AdminResponse();
                            adminResponse.setId(history.getUpdatedBy().getId());
                            adminResponse.setName(history.getUpdatedBy().getName());
                            adminResponse.setEmail(history.getUpdatedBy().getEmail());
                            adminResponse.setMobileNumber(history.getUpdatedBy().getMobileNumber());
                            historyResponse.setUpdatedBy(adminResponse);
                        }
                        
                        return historyResponse;
                    })
                    .collect(Collectors.toList());
        }
        response.setStatusHistory(statusHistoryResponses);
        
        return response;
    }

}