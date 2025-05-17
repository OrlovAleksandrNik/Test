package com.example.test.dao;

import com.example.test.dto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailsEmail(String email);
    Optional<User> findByPhonesPhone(String phone);
}
