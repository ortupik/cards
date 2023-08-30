package com.logicea.cards.repository;

import com.logicea.cards.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by their email address.
     * 
     * @param email the email address to search for.
     * @return the user entity if found, or null otherwise.
     */
    UserEntity findByEmail(String email);
}
