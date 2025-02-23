package com.me.techservices.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RequestOperatorDTO(
        @NotNull
        @Size(min = 2)
        String name,

        @NotNull
        @Size(min = 3, max = 20)
        String lastName
) {
}
