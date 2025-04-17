package com.jwelkam.jewelrymanagement.dto.request;

import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    
    private Long orderId;
    
    private String status;
    
    private String comments;
    
    private Long updatedById;
}