package com.jwelkam.jewelrymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;
    
    @Column(length = 1000)
    private String description;
    
    private Double amount;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;
    
    @Column(nullable = false)
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "from_city_id")
    private City fromCity;
    
    @ManyToOne
    @JoinColumn(name = "to_city_id")
    private City toCity;
    
    @ManyToOne
    @JoinColumn(name = "assigned_worker_id")
    private Worker assignedWorker;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderStatusHistory> statusHistory = new ArrayList<>();
    
    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }
}