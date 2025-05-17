package com.example.test.service;

import com.example.test.dto.UserDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService {
    Page<UserDto> searchUsers(
            Optional<String> name,
            Optional<String> phone,
            Optional<String> email,
            Optional<LocalDate> dateAfter,
            int page,
            int size
    );

    UserDto getCurrentUser(Long userId);
}
