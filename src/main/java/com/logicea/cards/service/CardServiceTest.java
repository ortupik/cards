package com.logicea.cards.service;

import com.logicea.cards.model.CardEntity;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.service.impl.CardServiceImpl;
import com.logicea.cards.dto.CardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CardServiceTest {

    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardRepository cardRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCard() {
        CardRequest request = new CardRequest();
        request.setName("Test Card");
        CardEntity card = new CardEntity();
        card.setName(request.getName());
        when(cardRepository.save(any(CardEntity.class))).thenReturn(card);

        CardEntity savedCard = cardService.createCard(request, "test@example.com");

        assertEquals(request.getName(), savedCard.getName());
    }

    @Test
    public void testGetCardById() {
        CardEntity card = new CardEntity();
        card.setId(1L);
        card.setName("Test Card");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        CardEntity fetchedCard = cardService.getCardById(1L);
        
        assertEquals(card.getId(), fetchedCard.getId());
        assertEquals(card.getName(), fetchedCard.getName());
    }

    @Test
    public void testUpdateCard() {
        CardEntity card = new CardEntity();
        card.setId(1L);
        card.setName("Old Card Name");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        CardRequest request = new CardRequest();
        request.setName("New Card Name");
        cardService.updateCard(1L, request);

        verify(cardRepository, times(1)).save(card);
        assertEquals(request.getName(), card.getName());
    }

    // ... Add more tests for other methods ...
}
