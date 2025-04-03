package com.application.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String paymentMethod;
    private String status;
    private ZonedDateTime paymentDate;
}
