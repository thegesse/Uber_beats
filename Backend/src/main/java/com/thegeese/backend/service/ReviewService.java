package com.thegeese.backend.service;

import com.thegeese.backend.dto.CreateReviewRequest;
import com.thegeese.backend.dto.ReviewResponse;
import com.thegeese.backend.model.Reviews;
import com.thegeese.backend.model.User;
import com.thegeese.backend.repository.ReviewRepository;
import com.thegeese.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    //get reviews, get review by email, get review from first/last name delete review

    private ReviewResponse toReviewDto(Reviews reviews) {
        return new ReviewResponse(
                reviews.getId(),
                reviews.getReviewer().getId(),
                reviews.getRating(),
                reviews.getComment()
        );
    }

    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request, Long authenticatedUserId) {
        User reviewer = userRepository.findById(authenticatedUserId).orElseThrow(() -> new RuntimeException("User not found"));

        User reviewed = userRepository.findById(request.reviewedId()).orElseThrow(() -> new RuntimeException("user not found"));

        Reviews review = new Reviews();
        review.setReviewer(reviewer);
        review.setReviewed(reviewed);
        review.setRating(request.rating());
        review.setComment(request.comment());

        Reviews saved = reviewRepository.save(review);

        reviewed.setRating(reviewRepository.averageRatingForUser(review.getId()));

        return new ReviewResponse(
                saved.getId(),
                reviewer.getId(),
                saved.getRating(),
                saved.getComment()
        );
    }

    // for admin use mostly
    public Page<ReviewResponse> getReviews(Pageable page) {
        return reviewRepository.findAll(page)
                .map(this::toReviewDto);
    }

    //use this for get all preferable
    public Page<ReviewResponse> getAllReviewsForUser(Pageable page, Long reviewedId) {
        return reviewRepository.findByReviewedId(reviewedId, page)
                .map(this::toReviewDto);
    }

    

}
