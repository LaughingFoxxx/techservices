package com.me.techservices.dto.request;

import java.math.BigDecimal;

public record RequestStatisticsDTO(
        Long bookingId,
        Long userId,
        String serviceName,
        BigDecimal finalPrice,
        String status
) {
}
