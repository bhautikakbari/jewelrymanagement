package com.jwelkam.jewelrymanagement.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwelkam.jewelrymanagement.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{
    Optional<City> findByName(String name);
}
