package com.example.test.service;

import com.example.test.dto.UpdateEmailRequest;

public interface EmailService {
    void addEmail(Long userId, UpdateEmailRequest request);
    void deleteEmail(Long userId, String email);
}
