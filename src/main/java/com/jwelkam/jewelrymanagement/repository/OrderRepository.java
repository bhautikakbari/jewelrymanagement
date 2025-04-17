package com.jwelkam.jewelrymanagement.repository;

import com.jwelkam.jewelrymanagement.entity.City;
import com.jwelkam.jewelrymanagement.entity.Order;
import com.jwelkam.jewelrymanagement.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByStatus(String status);
    List<Order> findByFromCity(City fromCity);
    List<Order> findByToCity(City toCity);
    List<Order> findByAssignedWorker(Worker worker);
}