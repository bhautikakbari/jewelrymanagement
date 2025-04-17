package com.jwelkam.jewelrymanagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwelkam.jewelrymanagement.entity.City;
import com.jwelkam.jewelrymanagement.entity.Worker;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByCity(City city);
    Optional<Worker> findByEmail(String email);
    Optional<Worker> findByMobileNumber(String mobileNumber);
}