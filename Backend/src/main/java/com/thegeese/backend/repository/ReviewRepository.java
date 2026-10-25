package com.thegeese.backend.repository;

import com.thegeese.backend.model.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    long countByReviewedId(Long reviewedId);

    @Query("select coalesce(avg(r.rating), 0) from Reviews r where r.reviewed.id = :userId")
    double averageRatingForUser(@Param("userId") Long userId);

    //reviewed find querries
    Page<Reviews> findByReviewedId(Long reviewedId, Pageable pageable);
    Page<Reviews> findByReviewedEmailIgnoreCase(String reviewedEmail, Pageable pageable);
    Page<Reviews> findByReviewedFirstnameIgnoreCase(
            String firstname,
            Pageable pageable
    );
    Page<Reviews> findByReviewedLastNameIgnoreCase(
            String lastName,
            Pageable pageable
    );
    Page<Reviews> findByReviewedFirstnameIgnoreCaseAndReviewedLastNameIgnoreCase(
            String firstname,
            String lastName,
            Pageable pageable
    );


    //reviewer finder querries
    Page<Reviews> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<Reviews> findByReviewerEmailIgnoreCase(String reviewerEmail, Pageable pageable);
    Page<Reviews> findByReviewerFirstnameIgnoreCase(
            String firstname,
            Pageable pageable
    );

    Page<Reviews> findByReviewerLastNameIgnoreCase(
            String lastName,
            Pageable pageable
    );

    Page<Reviews> findByReviewerFirstnameIgnoreCaseAndReviewerLastNameIgnoreCase(
            String firstname,
            String lastName,
            Pageable pageable
    );



}
