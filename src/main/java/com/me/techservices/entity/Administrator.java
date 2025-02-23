package com.me.techservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "administrators")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    String name;
}
