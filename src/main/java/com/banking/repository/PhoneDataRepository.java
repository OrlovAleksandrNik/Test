package com.banking.repository;

import com.banking.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    Optional<PhoneData> findByPhone(String phone);
    boolean existsByPhoneAndUserIdNot(String phone, Long userId);
} 