package com.me.techservices.service;

import com.me.techservices.dto.request.RequestBookingDTO;
import com.me.techservices.dto.request.RequestOperatorDTO;
import com.me.techservices.dto.request.RequestServiceDTO;
import com.me.techservices.dto.response.ResponseRevenueByDateDTO;
import com.me.techservices.entity.Booking;
import com.me.techservices.entity.Operator;
import com.me.techservices.entity.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TechService {

    Booking createBooking(RequestBookingDTO bookingDTO);

    Service createService(RequestServiceDTO serviceDTO);

    Service updateService(int id, RequestServiceDTO serviceDTO);

    List<Service> getServiceList();

    Service getServiceById(int id);

    Service cancelServiceBooking(int id);

    Booking getBookingByDateTime(LocalDateTime bookingDateTime);

    List<ResponseRevenueByDateDTO> getRevenueByDateTime(LocalDateTime startDate, LocalDateTime endDate);

    Operator createOperator(RequestOperatorDTO operatorDTO);

    Operator updateOperator(int id, RequestOperatorDTO operatorDTO);

    Booking getBookingForUser(Long userId, Long bookingId);

    Booking updateBookingOfUser(Long userId, Long bookingId, RequestBookingDTO bookingDTO);

    List<Booking> getAllBookingsList();

    Long updateAllBookingsDiscounts(String targetDiscount);

    Long deleteAllBookingsDiscounts();
}
