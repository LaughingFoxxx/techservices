package com.me.techservices.repository;

import com.me.techservices.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleType(UserRole.RoleType roleType);
}
