package com.logicea.cards.repository;

import com.logicea.cards.model.CardEntity;
import com.logicea.cards.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    /**
     * Finds all cards associated with a specific user.
     * 
     * @param user the user entity.
     * @return a list of card entities related to the given user.
     */
    List<CardEntity> findByUser(UserEntity user);

    /**
     * Finds a specific card by its ID and associated user.
     * 
     * @param id the unique ID of the card.
     * @param user the associated user entity.
     * @return the card entity if found, or null otherwise.
     */
    CardEntity findByIdAndUser(Long id, UserEntity user);

    // Other query methods related to Card can be added here.
}
