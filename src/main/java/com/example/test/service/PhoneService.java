package com.example.test.service;

import com.example.test.dto.UpdatePhoneRequest;

public interface PhoneService {
    void addPhone(Long userId, UpdatePhoneRequest request);
    void  deletePhone(Long userId, String phone);
}
