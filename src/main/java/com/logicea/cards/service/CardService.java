package com.logicea.cards.service;

import com.logicea.cards.dto.CardRequest;
import com.logicea.cards.model.CardEntity;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CardService {

    /**
     * Create a new card for a user.
     *
     * @param cardRequest The details of the card.
     * @param userEmail The email of the user creating the card.
     * @return The created card.
     */
    CardEntity createCard(CardRequest cardRequest, String userEmail);

    /**
     * Search and filter cards for a user.
     *
     * @param userEmail The email of the user.
     * @param name Filter by name of card.
     * @param color Filter by color of card.
     * @param status Filter by status of card.
     * @param page The page number.
     * @param size The size of the page.
     * @return A page of cards.
     */
    Page<CardEntity> searchCardsForUser(String userEmail, String name, String color, CardEntity.CardStatus status, Integer page, Integer size);

    /**
     * Retrieve a card by its ID.
     *
     * @param cardId The ID of the card.
     * @return The card.
     */
    CardEntity getCardById(Long cardId);

    /**
     * Update details of an existing card.
     *
     * @param cardId The ID of the card to update.
     * @param cardRequest The new details for the card.
     * @return The updated card.
     */
    CardEntity updateCard(Long cardId, CardRequest cardRequest);

    /**
     * Delete a card by its ID.
     *
     * @param cardId The ID of the card to delete.
     */
    void deleteCard(Long cardId);
}
