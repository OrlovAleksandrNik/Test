package com.banking.service;


import com.banking.entity.Account;
import com.banking.repository.AccountRepository;
import com.example.test.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private static final BigDecimal INCREASE_RATE = new BigDecimal("0.10"); // 10%
    private static final BigDecimal MAX_INCREASE_RATE = new BigDecimal("2.07"); // 207%
    private static final int INCREASE_INTERVAL = 30000; // 30 seconds

    private final AccountRepository accountRepository;

    @Transactional
    public void transfer(Long fromUserId, TransferRequest request) {
        Account fromAccount = accountRepository.findByUserIdWithLock(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account toAccount = accountRepository.findByUserIdWithLock(request.getToUserId())
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        BigDecimal amount = request.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        log.info("Transfer completed: {} from user {} to user {}", amount, fromUserId, request.getToUserId());
    }

    @Scheduled(fixedDelay = INCREASE_INTERVAL)
    @Transactional
    public void increaseBalances() {
        log.debug("Starting scheduled balance increase");
        accountRepository.findAll().forEach(this::increaseBalance);
    }

    private void increaseBalance(Account account) {
        BigDecimal maxBalance = account.getInitialBalance().multiply(BigDecimal.ONE.add(MAX_INCREASE_RATE));
        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(maxBalance) >= 0) {
            return;
        }

        BigDecimal increase = currentBalance.multiply(INCREASE_RATE)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal newBalance = currentBalance.add(increase);

        if (newBalance.compareTo(maxBalance) > 0) {
            newBalance = maxBalance;
        }

        account.setBalance(newBalance);
        account.setLastIncreaseTime(LocalDateTime.now());

        log.debug("Increased balance for account {}: {} -> {}", 
                account.getId(), currentBalance, newBalance);
    }
} 