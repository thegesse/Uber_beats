package com.thegeese.backend.dto;

import com.thegeese.backend.roles.Role;

public record UserDetailResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String country,
        String city,
        Double rating,
        Role role
) {
}
