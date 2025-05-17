package com.example.test.service;

import com.example.test.dto.TransferRequest;

public interface AccountService {
    void transfer(Long fromUserId, TransferRequest request);
    void incrementBalances();
}
