package com.banking.repository;

import com.banking.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
    Optional<EmailData> findByEmail(String email);
    boolean existsByEmailAndUserIdNot(String email, Long userId);
} 