package com.me.techservices.mapper;

import com.me.techservices.dto.request.RequestBookingDTO;
import com.me.techservices.dto.request.RequestOperatorDTO;
import com.me.techservices.dto.request.RequestServiceDTO;
import com.me.techservices.entity.Booking;
import com.me.techservices.entity.Operator;
import com.me.techservices.entity.Service;
import com.me.techservices.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class MapperDTOToEntity {
    public Booking mapRequestBookingDTOToBookingEntity(RequestBookingDTO requestBookingDTO) {
        User user = new User();
        user.setName(requestBookingDTO.userDTO().name());
        user.setLastName(requestBookingDTO.userDTO().lastName());
        user.setPhoneNumber(requestBookingDTO.userDTO().phoneNumber());
        user.setEmail(requestBookingDTO.userDTO().email());

        Service service = new Service();
        service.setName(requestBookingDTO.serviceDTO().name());
        service.setName(requestBookingDTO.serviceDTO().description());
        service.setName(requestBookingDTO.serviceDTO().price());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setService(service);
        booking.setStatus(requestBookingDTO.status());
        booking.setBookedTime(LocalDateTime.parse(requestBookingDTO.bookedTime()));

        return booking;
    }

    public Service mapRequestServiceDTOToServiceEntity(RequestServiceDTO requestServiceDTO) {
        Service service = new Service();
        service.setName(requestServiceDTO.name());
        service.setDescription(requestServiceDTO.description());
        service.setPrice(BigDecimal.valueOf(Long.parseLong(requestServiceDTO.price())));

        return service;
    }

    public Operator mapRequestOperatorDTOToOperatorEntity(RequestOperatorDTO requestOperatorDTO) {
        Operator operator = new Operator();
        operator.setName(requestOperatorDTO.name());
        operator.setLastName(requestOperatorDTO.lastName());
        return operator;
    }
}
