package com.thegeese.backend.service;

import com.thegeese.backend.dto.auth.AuthResponse;
import com.thegeese.backend.dto.auth.RegisterRequest;
import com.thegeese.backend.model.User;
import com.thegeese.backend.repository.UserRepository;
import com.thegeese.backend.roles.Role;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final Clock clock;

    public AuthService(UserRepository userRepository, Clock clock) {
        this.userRepository = userRepository;
        this.clock = clock;
    }

    public AuthResponse login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid email"));
        if(!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new AuthResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    private void requireAdult(LocalDate dateOfBirth) {
        if(dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now(clock).minusYears(18))) {
            throw new IllegalArgumentException("You must be at least 18 years old");
        }
    }

    public AuthResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("email already registered");
        }

        //TODO have a valid country and city validator
        //TODO add password hasher
        requireAdult(request.dateOfBirth());
        Role assignedRole = request.role() == Role.FADER ? Role.FADER : Role.USER;

        User user = new User();
        user.setFirstname(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setDateOfBirth(request.dateOfBirth());
        user.setCountry(request.country());
        user.setCity(request.city());
        user.setRole(assignedRole);
        user.setRating(0.0);

        User saved = userRepository.save(user);

        return new AuthResponse(saved.getId(), saved.getFirstname(), saved.getLastName(), saved.getEmail(), saved.getRole());

    }
}
