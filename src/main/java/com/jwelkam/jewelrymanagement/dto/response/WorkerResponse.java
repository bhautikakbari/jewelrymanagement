package com.jwelkam.jewelrymanagement.dto.response;

import lombok.Data;

@Data
public class WorkerResponse {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String address;
    private String photoPath;
    private CityResponse city;
}