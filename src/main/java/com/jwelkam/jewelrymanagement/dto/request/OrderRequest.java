package com.jwelkam.jewelrymanagement.dto.request;

import lombok.Data;

@Data
public class OrderRequest {
    
    private String description;
    
    private Double amount;
    
    private Long fromCityId;
    
    private Long toCityId;
    
    private Long assignedWorkerId;
}