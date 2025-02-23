package com.me.techservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "users_id", nullable = false)
    User user;

    @OneToOne
    @JoinColumn(name = "service", referencedColumnName = "services_id", nullable = false)
    Service service;

    @Column(name = "status")
    String status;

    @Column(name = "booked_time")
    LocalDateTime bookedTime;

    @Column(name = "discount")
    String discount;

    @OneToOne
    @JoinColumn(name = "operator", referencedColumnName = "operators_id")
    Operator operator;
}
