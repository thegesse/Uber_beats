package com.thegeese.backend.dto;

import com.thegeese.backend.roles.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RegisterRequest (
        @NotBlank(message = "First name required")
        String firstName,
        @NotBlank(message = "Last name required")
        String lastName,
        @NotBlank(message = "Email required") @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Birth date is required")
        LocalDate dateOfBirth,
        @NotBlank(message = "country is required")
        String country,
        String city,
        Double rating,
        Role role
) {
}
