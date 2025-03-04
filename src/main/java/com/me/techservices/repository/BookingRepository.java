package com.me.techservices.repository;

import com.me.techservices.dto.response.ResponseRevenueByDateDTO;
import com.me.techservices.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking getBookingByBookedTime(LocalDateTime bookingDateTime);

    @Query("SELECT new com.me.techservices.dto.response.ResponseRevenueByDateDTO(b.bookedTime, SUM(b.service.price)) " +
            "FROM Booking b " +
            "WHERE b.bookedTime BETWEEN :startDate AND :endDate " +
            "GROUP BY b.bookedTime")
    List<ResponseRevenueByDateDTO> findRevenueByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Optional<Booking> findByIdAndUserId(Long userId, Long bookingId);

    void deleteAllByUserId(Long userId);

    List<Booking> findByStatus(String provided);
}
