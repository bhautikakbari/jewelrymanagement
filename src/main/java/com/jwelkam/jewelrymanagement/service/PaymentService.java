package com.jwelkam.jewelrymanagement.service;

import com.jwelkam.jewelrymanagement.dto.request.PaymentRequest;
import com.jwelkam.jewelrymanagement.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentById(Long id);
    List<PaymentResponse> getAllPayments();
    List<PaymentResponse> getPaymentsByOrder(Long orderId);
    List<PaymentResponse> getPaymentsByStatus(String status);
    PaymentResponse updatePaymentStatus(Long id, String status);
    void deletePayment(Long id);
}