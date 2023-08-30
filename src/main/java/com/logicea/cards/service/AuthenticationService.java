package com.logicea.cards.service;

import com.logicea.cards.dto.LoginRequest;
import com.logicea.cards.model.UserEntity;
import com.logicea.cards.repository.UserRepository;
import com.logicea.cards.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String authenticate(LoginRequest loginRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // Generate JWT
        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail());
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail());
        }

        return jwtTokenProvider.generateToken(userEntity.getEmail());
    }
}
