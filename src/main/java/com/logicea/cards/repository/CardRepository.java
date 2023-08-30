package com.logicea.cards.repository;

import com.logicea.cards.model.CardEntity;
import com.logicea.cards.model.CardEntity.CardStatus;
import com.logicea.cards.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

     // Search by user and other criteria
    @Query("SELECT c FROM CardEntity c WHERE c.user = :user AND " +
           "(COALESCE(:name, null) IS NULL OR c.name LIKE %:name%) AND " +
           "(COALESCE(:color, null) IS NULL OR c.color = :color) AND " +
           "(COALESCE(:status, null) IS NULL OR c.status = :status)")
    Page<CardEntity> searchByUser(UserEntity user, String name, String color, CardStatus status, Pageable pageable);

    // Search all cards with criteria for admin
    @Query("SELECT c FROM CardEntity c WHERE " +
           "(COALESCE(:name, null) IS NULL OR c.name LIKE %:name%) AND " +
           "(COALESCE(:color, null) IS NULL OR c.color = :color) AND " +
           "(COALESCE(:status, null) IS NULL OR c.status = :status)")
    Page<CardEntity> searchAllCards(String name, String color, CardStatus status, Pageable pageable);

    // Other query methods related to Card can be added here.
}
