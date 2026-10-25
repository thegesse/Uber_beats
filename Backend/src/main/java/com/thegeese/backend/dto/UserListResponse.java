package com.thegeese.backend.dto;

public record UserListResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Double rating
) {
}
