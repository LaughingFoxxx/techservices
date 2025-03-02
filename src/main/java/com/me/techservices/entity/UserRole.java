package com.me.techservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleType roleType;

    public enum RoleType {
        ROLE_USER,
        ROLE_ADMIN
    }
}
