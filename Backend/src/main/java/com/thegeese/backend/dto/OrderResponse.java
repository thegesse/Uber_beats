package com.thegeese.backend.dto;

public record OrderResponse(
        Long id,
        Long sellerId,
        Long buyerId,
        Double price,
        Boolean completed
) {
}
