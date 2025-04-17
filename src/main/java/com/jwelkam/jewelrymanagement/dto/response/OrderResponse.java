package com.jwelkam.jewelrymanagement.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String description;
    private Double amount;
    private Date createdDate;
    private String status;
    private CityResponse fromCity;
    private CityResponse toCity;
    private WorkerResponse assignedWorker;
    private List<PaymentResponse> payments;
    private List<OrderStatusHistoryResponse> statusHistory;
}