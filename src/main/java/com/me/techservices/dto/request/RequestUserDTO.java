package com.me.techservices.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestUserDTO(
        @NotNull
        @Size(min = 2)
        String name,

        @NotNull
        @Size(min = 3)
        String lastName,

        @NotNull
        @Size(min = 11, max = 12)
        String phoneNumber,

        @NotNull
        @Email
        String email
) {
}
