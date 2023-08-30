package com.logicea.cards.service;

import com.logicea.cards.dto.CardRequest;
import com.logicea.cards.model.CardEntity;

import java.util.List;

public interface CardService {

    CardEntity createCard(CardRequest cardRequest, String userEmail);

    List<CardEntity> getCardsForUser(String userEmail);

    CardEntity updateCard(Long cardId, CardRequest cardRequest);

    void deleteCard(Long cardId);
}
