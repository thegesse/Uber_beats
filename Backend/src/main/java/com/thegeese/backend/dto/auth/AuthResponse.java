package com.thegeese.backend.dto.auth;

import com.thegeese.backend.roles.Role;

public record AuthResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
