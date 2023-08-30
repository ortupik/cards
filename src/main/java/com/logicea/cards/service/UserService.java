package com.logicea.cards.service;

import com.logicea.cards.model.UserEntity;

public interface UserService {
    
    /**
     * Retrieves a user by their unique ID.
     * 
     * @param userId the unique ID of the user.
     * @return the user entity, or null if not found.
     */
    UserEntity getUserById(Long userId);

    /**
     * Retrieves a user by their email address.
     * 
     * @param email the email address of the user.
     * @return the user entity, or null if not found.
     */
    UserEntity getUserByEmail(String email);
    
    // Any other user-related methods can be added as required.
}
