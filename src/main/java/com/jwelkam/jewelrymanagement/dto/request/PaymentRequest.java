package com.jwelkam.jewelrymanagement.dto.request;

import lombok.Data;

@Data
public class PaymentRequest {
    
    private Long orderId;
    
    private Double amount;
    
    private String paymentMethod;
}