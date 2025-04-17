package com.jwelkam.jewelrymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "order_status_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @Column(nullable = false)
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "status_date")
    private Date statusDate;
    
    @Column(length = 1000)
    private String comments;
    
    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private Admin updatedBy;
    
    @PrePersist
    public void prePersist() {
        this.statusDate = new Date();
    }
}