package com.logicea.cards.service;

import com.logicea.cards.exceptions.ResourceNotFoundException;
import com.logicea.cards.model.UserEntity;
import com.logicea.cards.repository.UserRepository;
import com.logicea.cards.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByEmail() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setRole(UserEntity.UserRole.MEMBER);

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        UserEntity fetchedUser = userService.findByEmail("test@example.com");

        assertEquals(user.getEmail(), fetchedUser.getEmail());
        assertEquals(user.getRole(), fetchedUser.getRole());
    }
    @Test
    public void testGetUserById() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRole(UserEntity.UserRole.MEMBER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity fetchedUser = userService.findById(1L);

        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals(user.getEmail(), fetchedUser.getEmail());
    }

     @Test
    public void testUserNotFoundByEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findByEmail("test@example.com");
        });
    }

}
