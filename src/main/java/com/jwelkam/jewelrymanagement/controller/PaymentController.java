package com.jwelkam.jewelrymanagement.controller;

import com.jwelkam.jewelrymanagement.dto.request.PaymentRequest;
import com.jwelkam.jewelrymanagement.dto.response.PaymentResponse;
import com.jwelkam.jewelrymanagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    private final PaymentService paymentService;
    
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.createPayment(paymentRequest), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments()); 
    }
    
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentsByOrder(orderId));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }
    
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Long id, @PathVariable String status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, status));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}