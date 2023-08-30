package com.logicea.cards.service;

import com.logicea.cards.model.UserEntity;

import java.util.List;

public interface UserService {

    /**
     * Authenticate a user with their email and password.
     *
     * @param email        Email of the user.
     * @param rawPassword  Raw password provided by the user.
     * @return A JWT token for valid users.
     */
    String authenticate(String email, String rawPassword);

    /**
     * Register a new user.
     *
     * @param user  User details.
     * @return Registered user entity.
     */
    UserEntity register(UserEntity user);

    /**
     * Update details of an existing user.
     *
     * @param updatedUser  Updated user details.
     * @return Updated user entity.
     */
    UserEntity updateUser(UserEntity updatedUser);

    /**
     * Fetch all users in the system.
     *
     * @return List of all users.
     */
    List<UserEntity> getAllUsers();

    /**
     * Fetch a user by their ID.
     *
     * @param userId  ID of the user.
     * @return User entity.
     */
    UserEntity getUserById(Long userId);

    /**
     * Delete a user based on their ID.
     *
     * @param userId  ID of the user.
     */
    void deleteUser(Long userId);
}
