package com.banking.service;

import com.banking.dto.AuthenticationRequest;
import com.banking.dto.AuthenticationResponse;
import com.banking.entity.User;
import com.banking.repository.UserRepository;
import com.banking.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getLogin())
                .orElseGet(() -> userRepository.findByPhone(request.getLogin())
                        .orElseThrow(() -> new IllegalArgumentException("User not found")));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(request.getLogin())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();

        String token = jwtService.generateToken(userDetails, user.getId());
        return new AuthenticationResponse(token);
    }
} 