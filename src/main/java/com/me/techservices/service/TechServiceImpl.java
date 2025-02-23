package com.me.techservices.service;

import com.me.techservices.dto.request.RequestBookingDTO;
import com.me.techservices.dto.request.RequestServiceDTO;
import com.me.techservices.dto.response.ResponseRevenueByDateDTO;
import com.me.techservices.entity.Booking;
import com.me.techservices.entity.Service;
import com.me.techservices.entity.User;
import com.me.techservices.exception.ServiceException;
import com.me.techservices.repository.BookingRepository;
import com.me.techservices.repository.ServiceRepository;
import com.me.techservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class TechServiceImpl implements TechService {
    BookingRepository bookingRepository;
    ServiceRepository serviceRepository;
    UserRepository userRepository;

    @Override
    public Booking createBooking(RequestBookingDTO bookingDTO) {
        User user = new User();
        user.setName(bookingDTO.userDTO().name());
        user.setLastName(bookingDTO.userDTO().lastName());
        user.setPhoneNumber(bookingDTO.userDTO().phoneNumber());
        user.setEmail(bookingDTO.userDTO().email());

        Service service = new Service();
        service.setName(bookingDTO.serviceDTO().name());
        service.setName(bookingDTO.serviceDTO().description());
        service.setName(bookingDTO.serviceDTO().price());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setService(service);
        booking.setStatus(bookingDTO.status());
        booking.setBookedTime(LocalDateTime.parse(bookingDTO.bookedTime()));

        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Service createService(RequestServiceDTO serviceDTO) {
        Service service = new Service();
        service.setName(serviceDTO.name());
        service.setDescription(serviceDTO.description());
        service.setPrice(BigDecimal.valueOf(Long.parseLong(serviceDTO.price())));

        return service;
    }

    @Override
    public Service updateService(int id, RequestServiceDTO serviceDTO) {
        Service service = serviceRepository
                .findById((long) id)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанная услуга не найдена"));

        service.setName(serviceDTO.name());
        service.setDescription(serviceDTO.description());
        service.setPrice(BigDecimal.valueOf(Long.parseLong(serviceDTO.price())));

        serviceRepository.save(service);

        return service;
    }

    @Override
    public List<Service> getServiceList() {
        return serviceRepository.findAll();
    }

    @Override
    public Service getServiceById(int id) {
        return serviceRepository
                .findById((long) id)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанная услуга не найдена"));
    }

    @Override
    public Service cancelServiceBooking(int id) {
        Service service = serviceRepository
                .findById((long) id)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанная услуга не найдена"));
        serviceRepository.deleteById((long) id);
        return service;
    }

    @Override
    public Booking getBookingByDateTime(LocalDateTime bookingDateTime) {
        return bookingRepository.getBookingByBookedTime(bookingDateTime);
    }

    @Override
    public List<ResponseRevenueByDateDTO> getRevenueByDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findRevenueByDate(startDate, endDate);
    }
}
