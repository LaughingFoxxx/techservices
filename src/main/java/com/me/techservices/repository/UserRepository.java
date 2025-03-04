package com.me.techservices.repository;

import com.me.techservices.entity.Booking;
import com.me.techservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBookingsEmpty(List<Booking> bookings);
}
