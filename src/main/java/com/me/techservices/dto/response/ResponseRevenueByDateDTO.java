package com.me.techservices.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ResponseRevenueByDateDTO(
        LocalDateTime date,
        BigDecimal revenue
) {
}
