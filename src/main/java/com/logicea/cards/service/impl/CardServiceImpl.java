package com.logicea.cards.service.impl;

import com.logicea.cards.dto.CardRequest;
import com.logicea.cards.exceptions.ResourceNotFoundException;
import com.logicea.cards.model.CardEntity;
import com.logicea.cards.model.UserEntity;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.repository.UserRepository;
import com.logicea.cards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CardEntity createCard(CardRequest cardRequest, String userEmail) {
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        if (userEntity == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }

        CardEntity cardEntity = new CardEntity();
        cardEntity.setName(cardRequest.getName());
        cardEntity.setDescription(cardRequest.getDescription());
        cardEntity.setColor(cardRequest.getColor());
        cardEntity.setUser(userEntity);
        
        return cardRepository.save(cardEntity);
    }

    @Override
    public List<CardEntity> getCardsForUser(String userEmail) {
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        if (userEntity == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }
        
        if (UserEntity.UserRole.ADMIN.equals(userEntity.getRole())) {
            return cardRepository.findAll();
        } else {
            return cardRepository.findByUser(userEntity);
        }
    }

    @Override
    public CardEntity updateCard(Long cardId, CardRequest cardRequest) {
        CardEntity cardEntity = cardRepository.findById(cardId)
            .orElseThrow(() -> new ResourceNotFoundException("Card not found with ID: " + cardId));
            
        cardEntity.setName(cardRequest.getName());
        cardEntity.setDescription(cardRequest.getDescription());
        cardEntity.setColor(cardRequest.getColor());

        return cardRepository.save(cardEntity);
    }

    @Override
    public void deleteCard(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new ResourceNotFoundException("Card not found with ID: " + cardId);
        }
        
        cardRepository.deleteById(cardId);
    }
}
