package com.jwelkam.jewelrymanagement.repository;

import com.jwelkam.jewelrymanagement.entity.Order;
import com.jwelkam.jewelrymanagement.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrder(Order order);
    List<Payment> findByStatus(String status);
}