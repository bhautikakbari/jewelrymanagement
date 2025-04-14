package com.jwelkam.jewelrymanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwelkam.jewelrymanagement.dto.request.CityRequest;
import com.jwelkam.jewelrymanagement.dto.response.CityResponse;
import com.jwelkam.jewelrymanagement.service.CityService;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/create")
    public ResponseEntity<CityResponse> createCity(@RequestBody CityRequest cityRequest){
        return new ResponseEntity<>(cityService.createCity(cityRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getcity/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id){
        return new ResponseEntity<>(cityService.getCityById(id),HttpStatus.OK);
    }

    @GetMapping("/getcity/name/{name}")
    public ResponseEntity<CityResponse> getCityByName(@PathVariable String name){
        return new ResponseEntity<>(cityService.getCityByName(name), HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CityResponse>> getAllCity(){
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CityResponse> updateCity(@PathVariable Long id, CityRequest cityRequest){
        return new ResponseEntity<>(cityService.updateCity(id, cityRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id){
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
