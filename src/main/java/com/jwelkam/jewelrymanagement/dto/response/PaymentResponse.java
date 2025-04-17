package com.jwelkam.jewelrymanagement.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private Double amount;
    private String status;
    private Date paymentDate;
    private String paymentMethod;
}