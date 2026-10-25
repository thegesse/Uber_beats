package com.thegeese.backend.dto.review;

public record ReviewResponse(
        Long id,
        Long reviewer,
        Integer rating,
        String comment
) {
}
