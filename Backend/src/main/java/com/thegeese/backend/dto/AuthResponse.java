package com.thegeese.backend.dto;

import com.thegeese.backend.roles.Role;

import java.time.LocalDate;

public record AuthResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
