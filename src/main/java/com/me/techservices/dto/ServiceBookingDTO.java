package com.me.techservices.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.me.techservices.constant.ServiceServices;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ServiceBookingDTO (
        @NotNull
        @Size(min = 2)
        String name,

        @NotNull
        @Size(min = 2)
        String lastName,

        @NotNull
        @Future
        LocalDateTime bookingServiceTime,

        @NotNull
        ServiceServices serviceService
) {
}
