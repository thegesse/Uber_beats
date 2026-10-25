package com.thegeese.backend.dto;

public record ReviewResponse(
        Long id,
        Long reviewer,
        Integer rating,
        String comment
) {
}
