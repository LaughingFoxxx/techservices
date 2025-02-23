package com.me.techservices.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestServiceDTO(
        @NotNull
        @Size(min = 3, max = 15)
        String name,

        @NotNull
        @Size(max = 100)
        String description,

        @NotNull
        String price
) {
}
