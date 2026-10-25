package com.thegeese.backend.dto;

import com.thegeese.backend.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateReviewRequest(
        @NotNull(message = "Must have a reviewed user")
        Long reviewedId,
        @NotNull(message = "Cant be null") @Positive
        Integer rating,
        String comment
) {
}
