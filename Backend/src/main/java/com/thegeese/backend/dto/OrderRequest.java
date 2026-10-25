package com.thegeese.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderRequest (
        @NotNull(message = "must have a buyer")
        Long buyerId,
        @NotNull(message = "must have a seller")
        Long sellerId,
        @NotNull
        @Positive
        Double price,
        Boolean completed,
        LocalDateTime completedAt
){
}
