package com.me.techservices.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RequestBookingDTO(
        @NotNull
        RequestUserDTO userDTO,

        @NotNull
        RequestServiceDTO serviceDTO,

        @NotNull
        String status,

        @NotNull
        String bookedTime
) {
}
