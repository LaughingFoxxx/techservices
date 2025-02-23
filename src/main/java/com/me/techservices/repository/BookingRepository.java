package com.me.techservices.repository;

import com.me.techservices.dto.response.ResponseRevenueByDateDTO;
import com.me.techservices.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking getBookingByBookedTime(LocalDateTime bookingDateTime);

    @Query("SELECT new com.me.techservices.dto.response.ResponseRevenueByDateDTO(b.bookedTime, SUM(b.service.price)) " +
            "FROM Booking b " +
            "WHERE b.bookedTime BETWEEN :startDate AND :endDate " +
            "GROUP BY b.bookedTime")
    List<ResponseRevenueByDateDTO> findRevenueByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
