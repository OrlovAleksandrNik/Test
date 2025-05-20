package com.banking.service;

import com.banking.dto.EmailDto;
import com.banking.dto.PhoneDto;
import com.banking.dto.UserDto;
import com.banking.entity.EmailData;
import com.banking.entity.PhoneData;
import com.banking.entity.User;
import com.banking.repository.EmailDataRepository;
import com.banking.repository.PhoneDataRepository;
import com.banking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailDataRepository emailDataRepository;
    private final PhoneDataRepository phoneDataRepository;

    @Cacheable(value = "users", key = "#id")
    public UserDto getUserById(Long id) {
        User user = userRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return mapToDto(user);
    }

    public Page<UserDto> searchUsers(LocalDate dateOfBirth, String phone, String name, String email, Pageable pageable) {
        Specification<User> spec = Specification.where(null);

        if (dateOfBirth != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("dateOfBirth"), dateOfBirth));
        }

        if (phone != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.join("phones").get("phone"), phone));
        }

        if (name != null) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("name"), name + "%"));
        }

        if (email != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.join("emails").get("email"), email));
        }

        return userRepository.findAll(spec, pageable).map(this::mapToDto);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#userId")
    public void addEmail(Long userId, EmailDto emailDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (emailDataRepository.existsByEmailAndUserIdNot(emailDto.getEmail(), userId)) {
            throw new IllegalArgumentException("Email already exists");
        }

        EmailData emailData = new EmailData();
        emailData.setEmail(emailDto.getEmail());
        emailData.setUser(user);
        user.getEmails().add(emailData);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#userId")
    public void addPhone(Long userId, PhoneDto phoneDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (phoneDataRepository.existsByPhoneAndUserIdNot(phoneDto.getPhone(), userId)) {
            throw new IllegalArgumentException("Phone already exists");
        }

        PhoneData phoneData = new PhoneData();
        phoneData.setPhone(phoneDto.getPhone());
        phoneData.setUser(user);
        user.getPhones().add(phoneData);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#userId")
    public void deleteEmail(Long userId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        EmailData emailData = user.getEmails().stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        user.getEmails().remove(emailData);
        emailDataRepository.delete(emailData);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#userId")
    public void deletePhone(Long userId, String phone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PhoneData phoneData = user.getPhones().stream()
                .filter(p -> p.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Phone not found"));

        user.getPhones().remove(phoneData);
        phoneDataRepository.delete(phoneData);
    }

    private UserDto mapToDto(User user) {
        List<String> emails = user.getEmails().stream()
                .map(EmailData::getEmail)
                .collect(Collectors.toList());

        List<String> phones = user.getPhones().stream()
                .map(PhoneData::getPhone)
                .collect(Collectors.toList());

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getDateOfBirth(),
                emails,
                phones
        );
    }
} 