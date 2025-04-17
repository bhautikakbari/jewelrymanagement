package com.jwelkam.jewelrymanagement.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStatusHistoryResponse {
    private Long id;
    private Long orderId;
    private String status;
    private Date statusDate;
    private String comments;
    private AdminResponse updatedBy;
}