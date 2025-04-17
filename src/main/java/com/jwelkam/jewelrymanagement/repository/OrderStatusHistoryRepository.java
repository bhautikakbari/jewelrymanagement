package com.jwelkam.jewelrymanagement.repository;

import com.jwelkam.jewelrymanagement.entity.Order;
import com.jwelkam.jewelrymanagement.entity.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    List<OrderStatusHistory> findByOrderOrderByStatusDateDesc(Order order);
}