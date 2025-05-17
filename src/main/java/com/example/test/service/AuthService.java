package com.example.test.service;

import com.example.test.dto.AuthRequest;
import com.example.test.dto.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
}
