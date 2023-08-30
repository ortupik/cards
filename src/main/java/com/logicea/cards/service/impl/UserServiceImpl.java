package com.logicea.cards.service.impl;

import com.logicea.cards.exceptions.ResourceNotFoundException;
import com.logicea.cards.model.UserEntity;
import com.logicea.cards.repository.UserRepository;
import com.logicea.cards.security.JwtTokenProvider;
import com.logicea.cards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String authenticate(String email, String rawPassword) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, rawPassword)
            );
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null || !passwordEncoder.matches(rawPassword, userEntity.getPassword())) {
                throw new BadCredentialsException("Invalid email or password");
            }
            return jwtTokenProvider.generateToken(userEntity.getEmail());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password", e);
        }
    }

    public UserEntity register(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity updatedUser) {
        Optional<UserEntity> existingUser = userRepository.findById(updatedUser.getId());
        if (!existingUser.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + updatedUser.getId());
        }
        if (updatedUser.getPassword() != null) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return userRepository.save(updatedUser);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
