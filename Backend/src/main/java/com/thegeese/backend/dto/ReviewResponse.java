package com.thegeese.backend.dto;

import com.thegeese.backend.model.User;

public record ReviewResponse(
        User reviewer,
        int rating,
        String comment
) {
}
