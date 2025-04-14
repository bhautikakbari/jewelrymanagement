package com.jwelkam.jewelrymanagement.service_impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwelkam.jewelrymanagement.dto.request.CityRequest;
import com.jwelkam.jewelrymanagement.dto.response.CityResponse;
import com.jwelkam.jewelrymanagement.entity.City;
import com.jwelkam.jewelrymanagement.exception.BadRequestException;
import com.jwelkam.jewelrymanagement.exception.ResourceNotFoundException;
import com.jwelkam.jewelrymanagement.repository.CityRepository;
import com.jwelkam.jewelrymanagement.service.CityService;

@Service
public class CityServiceImpl implements CityService {
    
    private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CityResponse createCity(CityRequest cityRequest) {
        if (cityRepository.findByName(cityRequest.getName()).isPresent()) {
            throw new BadRequestException("City with this name already exists");
        }
        log.info("Creating city: {}", cityRequest.getName());

        City city = City.builder().name(cityRequest.getName()).state(cityRequest.getState())
                .country(cityRequest.getCountry()).build();

        City savedCity = cityRepository.save(city);
        return modelMapper.map(savedCity, CityResponse.class);
    }

    @Override
    public CityResponse getCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City", "id", id));
        return modelMapper.map(city, CityResponse.class);

    }

    @Override
    public CityResponse getCityByName(String name) {
        City city = cityRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("City", "name", name));
        return modelMapper.map(city, CityResponse.class);
    }

    @Override
    public List<CityResponse> getAllCities() {

        List<City> cities = cityRepository.findAll();

        Type listType = new TypeToken<List<CityResponse>>() {
        }.getType();
        return modelMapper.map(cities, listType);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest cityRequest) {
        City city = cityRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("City", "id", id));

        if(!city.getName().equals(cityRequest.getName()) && cityRepository.findByName(cityRequest.getName()).isPresent()){
            throw new BadRequestException("City with this name already exists");
        }

        City updatedCity = City.builder().name(cityRequest.getName()).state(cityRequest.getState()).country(cityRequest.getCountry()).build();
        City savedCity = cityRepository.save(updatedCity);
        return modelMapper.map(savedCity, CityResponse.class);
    }

    @Override
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("City", "id", id));
        cityRepository.delete(city);
    }

}
