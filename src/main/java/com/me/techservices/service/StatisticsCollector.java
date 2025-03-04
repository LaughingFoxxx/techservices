package com.me.techservices.service;

import com.me.techservices.dto.request.RequestStatisticsDTO;
import com.me.techservices.entity.Booking;
import com.me.techservices.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsCollector {

    private final BookingRepository bookingRepository;

    public List<RequestStatisticsDTO> collectStatistics() {
        List<Booking> bookings = bookingRepository.findByStatus("PROVIDED");

        return bookings.stream()
                .map(booking -> new RequestStatisticsDTO(booking.getId(), booking.getUser().getId(), booking.getService().getName(), calculateFinalPrice(booking), booking.getStatus()))
                .collect(Collectors.toList());
    }

    private BigDecimal calculateFinalPrice(Booking booking) {
        BigDecimal price = booking.getService().getPrice();
        if (booking.getDiscount() != null && !booking.getDiscount().isEmpty()) {
            BigDecimal discount = new BigDecimal(booking.getDiscount());
            return price.subtract(price.multiply(discount));
        }
        return price;
    }
}
