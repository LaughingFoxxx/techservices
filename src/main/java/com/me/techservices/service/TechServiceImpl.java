package com.me.techservices.service;

import com.me.techservices.dto.request.RequestBookingDTO;
import com.me.techservices.dto.request.RequestOperatorDTO;
import com.me.techservices.dto.request.RequestServiceDTO;
import com.me.techservices.dto.response.ResponseRevenueByDateDTO;
import com.me.techservices.entity.Booking;
import com.me.techservices.entity.Operator;
import com.me.techservices.entity.Service;
import com.me.techservices.exception.ServiceException;
import com.me.techservices.mapper.MapperDTOToEntity;
import com.me.techservices.repository.BookingRepository;
import com.me.techservices.repository.OperatorRepository;
import com.me.techservices.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class TechServiceImpl implements TechService {
    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final OperatorRepository operatorRepository;
    private final MapperDTOToEntity dtoMapper;


    @Override
    public Booking createBooking(RequestBookingDTO bookingDTO) {
        return bookingRepository.save(dtoMapper.mapRequestBookingDTOToBookingEntity(bookingDTO));
    }

    @Override
    public Service createService(RequestServiceDTO serviceDTO) {
        return dtoMapper.mapRequestServiceDTOToServiceEntity(serviceDTO);
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

    @Override
    public Operator createOperator(RequestOperatorDTO operatorDTO) {
        operatorRepository.save(dtoMapper.mapRequestOperatorDTOToOperatorEntity(operatorDTO));
        return operatorRepository.findOperatorByLastName(operatorDTO.lastName());
    }

    @Override
    public Operator updateOperator(int id, RequestOperatorDTO operatorDTO) {
        Operator operator = operatorRepository
                .findById((long) id)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанный оператор не найден"));

        operator.setName(operatorDTO.name());
        operator.setLastName(operatorDTO.lastName());

        operatorRepository.save(operator);
        return operator;
    }

    @Override
    public Booking getBookingForUser(Long userId, Long bookingId) {
        return bookingRepository
                .findByIdAndUserId(userId, bookingId)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанный оператор не найден"));
    }

    @Override
    @Transactional
    public Booking updateBookingOfUser(Long userId, Long bookingId, RequestBookingDTO bookingDTO) {
        Booking existingBooking = getBookingForUser(userId, bookingId);

        existingBooking.setStatus(bookingDTO.status());
        existingBooking.setBookedTime(LocalDateTime.parse(bookingDTO.bookedTime()));

        bookingRepository.save(existingBooking);

        return existingBooking;
    }

    @Override
    public Long updateAllBookingsDiscounts(String targetDiscount) {
        List<Booking> list = getAllBookingsList();

        long affectedBookingsCount = 0L;
        for (Booking booking : list) {
            booking.setDiscount(targetDiscount);
            affectedBookingsCount += 1;
        }

        return affectedBookingsCount;
    }

    @Override
    public Long deleteAllBookingsDiscounts() {
        List<Booking> list = getAllBookingsList();

        long affectedBookingsCount = 0L;
        for (Booking booking : list) {
            booking.setDiscount("0%");
            affectedBookingsCount += 1;
        }

        return affectedBookingsCount;
    }

    @Override
    public List<Booking> getAllBookingsList() {
        return bookingRepository.findAll();
    }
}
