package com.logicea.cards.controller;

import com.logicea.cards.dto.CardRequest;
import com.logicea.cards.dto.CardResponse;
import com.logicea.cards.model.CardEntity;
import com.logicea.cards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest cardRequest) {
        CardEntity createdCard = cardService.createCard(cardRequest);
        return ResponseEntity.ok(mapToCardResponse(createdCard));
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> getCards() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<CardEntity> cards = cardService.getCardsForUserEmail(userEmail);
        List<CardResponse> cardResponses = cards.stream()
                .map(this::mapToCardResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cardResponses);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> getCard(@PathVariable Long cardId) {
        CardEntity card = cardService.getCardById(cardId);
        return ResponseEntity.ok(mapToCardResponse(card));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable Long cardId, @RequestBody CardRequest cardRequest) {
        CardEntity updatedCard = cardService.updateCard(cardId, cardRequest);
        return ResponseEntity.ok(mapToCardResponse(updatedCard));
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build();
    }

    private CardResponse mapToCardResponse(CardEntity cardEntity) {
        // Mapping logic from CardEntity to CardResponse goes here.
        // You can use tools like MapStruct or do it manually.
        // For the purpose of this example, it's done manually.
        CardResponse response = new CardResponse();
        response.setId(cardEntity.getId());
        response.setName(cardEntity.getName());
        response.setDescription(cardEntity.getDescription());
        response.setColor(cardEntity.getColor());
        response.setStatus(cardEntity.getStatus());
        response.setDateOfCreation(cardEntity.getCreatedAt());
        return response;
    }
}
