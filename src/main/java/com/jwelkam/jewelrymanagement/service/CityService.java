package com.jwelkam.jewelrymanagement.service;

import java.util.List;

import com.jwelkam.jewelrymanagement.dto.request.CityRequest;
import com.jwelkam.jewelrymanagement.dto.response.CityResponse;

public interface CityService {
    CityResponse createCity(CityRequest cityRequest);
    CityResponse getCityById(Long id);
    CityResponse getCityByName(String name);
    List<CityResponse> getAllCities();
    CityResponse updateCity(Long id, CityRequest cityRequest);
    void deleteCity(Long id);
}
