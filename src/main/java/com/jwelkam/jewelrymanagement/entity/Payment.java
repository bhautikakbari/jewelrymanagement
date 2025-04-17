package com.jwelkam.jewelrymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @Column(nullable = false)
    private Double amount;
    
    @Column(nullable = false)
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_date")
    private Date paymentDate;
    
    @Column(name = "payment_method")
    private String paymentMethod;
    
    @PrePersist
    public void prePersist() {
        this.paymentDate = new Date();
    }
}