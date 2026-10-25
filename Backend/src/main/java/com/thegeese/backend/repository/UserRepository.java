package com.thegeese.backend.repository;

import com.thegeese.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Page<User> findByCountryIgnoreCase(String country, Pageable pageable);
    Page<User> findByCityIgnoreCase(String country, Pageable pageable);
    Page<User> findByCityIgnoreCaseAndByCountryIgnoreCase(String city, String country, Pageable pageable);
    Page<User> findByFirstNameIgnoreCase(String firstName, Pageable pageable);
    Page<User> findByLastNameIgnoreCase(String lastName, Pageable pageable);
}
