package com.thegeese.backend.repository;

import com.thegeese.backend.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {

}
