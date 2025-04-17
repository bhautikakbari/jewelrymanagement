package com.jwelkam.jewelrymanagement.service_impl;

import com.jwelkam.jewelrymanagement.dto.request.PaymentRequest;
import com.jwelkam.jewelrymanagement.dto.response.PaymentResponse;
import com.jwelkam.jewelrymanagement.entity.Order;
import com.jwelkam.jewelrymanagement.entity.Payment;
import com.jwelkam.jewelrymanagement.exception.ResourceNotFoundException;
import com.jwelkam.jewelrymanagement.repository.OrderRepository;
import com.jwelkam.jewelrymanagement.repository.PaymentRepository;
import com.jwelkam.jewelrymanagement.service.PaymentService;
import com.jwelkam.jewelrymanagement.util.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    
    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }
    
    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", paymentRequest.getOrderId()));
        
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(paymentRequest.getAmount());
        payment.setStatus(PaymentStatus.PENDING.name());
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        
        Payment savedPayment = paymentRepository.save(payment);
        
        return mapToPaymentResponse(savedPayment);
    }
    
    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        
        return mapToPaymentResponse(payment);
    }
    
    @Override
    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        
        return payments.stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentResponse> getPaymentsByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        
        List<Payment> payments = paymentRepository.findByOrder(order);
        
        return payments.stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentResponse> getPaymentsByStatus(String status) {
        List<Payment> payments = paymentRepository.findByStatus(status);
        
        return payments.stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public PaymentResponse updatePaymentStatus(Long id, String status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        
        payment.setStatus(status);
        Payment updatedPayment = paymentRepository.save(payment);
        
        return mapToPaymentResponse(updatedPayment);
    }
    
    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        
        paymentRepository.delete(payment);
    }
    
    private PaymentResponse mapToPaymentResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setOrderId(payment.getOrder().getId());
        response.setAmount(payment.getAmount());
        response.setStatus(payment.getStatus());
        response.setPaymentDate(payment.getPaymentDate());
        response.setPaymentMethod(payment.getPaymentMethod());
        return response;
    }
}