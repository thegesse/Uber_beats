package com.thegeese.backend.service;

import com.thegeese.backend.dto.user.UserDetailResponse;
import com.thegeese.backend.dto.user.UserListResponse;
import com.thegeese.backend.model.User;
import com.thegeese.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //TODO make a mapper for these bits of code
    private UserListResponse toUserListDto(User user) {
        return new UserListResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastName(),
                user.getEmail(),
                user.getRating()
        );
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
    //TODO update profile info method
    public Page<UserListResponse> getUsers(Pageable page, String country, String city) {
        country = blankToNull(country);
        city = blankToNull(city);
        Page<User> users;

        if (country != null && city != null) {
            users = userRepository.findByCityIgnoreCaseAndCountryIgnoreCase(city, country, page);
        } else if (country != null) {
            users = userRepository.findByCountryIgnoreCase(country, page);
        }else if (city != null) {
            users = userRepository.findByCityIgnoreCase(city, page);
        } else {
            users = userRepository.findAll(page);
        }

        return users.map(this::toUserListDto);
    }

    public UserListResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toUserListDto)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public Page<UserListResponse> getUserByFirstName(String firstName, Pageable page) {
        return userRepository.findByFirstnameIgnoreCase(firstName, page)
                .map(this::toUserListDto);
    }

    public Page<UserListResponse> getUserByLastName(String lastName, Pageable page) {
        return userRepository.findByLastNameIgnoreCase(lastName, page)
                .map(this::toUserListDto);
    }

    public UserListResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::toUserListDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDetailResponse getUserDetails(Long userId) {
        return userRepository.findById(userId)
                .map(user -> new UserDetailResponse(
                        user.getId(),
                        user.getFirstname(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getCountry(),
                        user.getCity(),
                        user.getRating(),
                        user.getRole()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new RuntimeException("user doesnt exist");
        }
        userRepository.deleteById(userId);
    }
}
