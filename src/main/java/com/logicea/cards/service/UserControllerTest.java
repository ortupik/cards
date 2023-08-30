package com.logicea.cards.controller;

import com.logicea.cards.service.UserService;
import com.logicea.cards.controller.UserController;
import com.logicea.cards.exceptions.ResourceNotFoundException;
import com.logicea.cards.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setRole(UserEntity.UserRole.MEMBER);

        when(userService.findByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(get("/api/users/test@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test@example.com")));

    }

    @Test
    public void testUserNotFoundByEmail() throws Exception {
        when(userService.findByEmail("nonexistent@example.com")).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/nonexistent@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testGetUserById() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRole(UserEntity.UserRole.MEMBER);

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test@example.com")));
    }
}
