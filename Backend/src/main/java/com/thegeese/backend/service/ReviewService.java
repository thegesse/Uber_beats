package com.thegeese.backend.service;

import com.thegeese.backend.dto.ReviewResponse;
import com.thegeese.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewResponse createReview()
}
