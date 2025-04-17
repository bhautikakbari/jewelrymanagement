package com.jwelkam.jewelrymanagement.dto.request;

import lombok.Data;

@Data
public class WorkerRequest {

    private String name;

    private String email;

    private String mobileNumber;

    private String address;

    private Long cityId;
}
