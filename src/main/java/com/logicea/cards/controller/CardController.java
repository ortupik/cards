package com.logicea.cards.controller;

import com.logicea.cards.dto.CardRequest;
import com.logicea.cards.model.CardEntity;
import com.logicea.cards.service.CardService;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    
    @ApiOperation(value = "Add a new card")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Card successfully created"),
            @ApiResponse(code = 400, message = "Invalid input data")
    })
    // Create a new card
    @PostMapping
    public ResponseEntity<CardEntity> createCard(@RequestBody CardRequest cardRequest, Principal principal) {
        return ResponseEntity.ok(cardService.createCard(cardRequest, principal.getName()));
    }

    // Get cards for an authenticated user
    @GetMapping
    public ResponseEntity<List<CardEntity>> getCardsForUser(
            Principal principal,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) CardEntity.CardStatus status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        Page<CardEntity> cards = cardService.searchCardsForUser(principal.getName(), name, color, status, page, size);
        return ResponseEntity.ok(cards.getContent());
    }

     @ApiOperation(value = "Get a card by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved card"),
            @ApiResponse(code = 404, message = "Card not found")
    })
    // Get a specific card by its ID
    @GetMapping("/{id}")
    public ResponseEntity<CardEntity> getCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @ApiOperation(value = "Update an existing card")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated card"),
            @ApiResponse(code = 404, message = "Card not found"),
            @ApiResponse(code = 400, message = "Invalid input data")
    })
    // Update a card's details
    @PutMapping("/{id}")
    public ResponseEntity<CardEntity> updateCard(@PathVariable Long id, @RequestBody CardRequest cardRequest) {
        return ResponseEntity.ok(cardService.updateCard(id, cardRequest));
    }

    @ApiOperation(value = "Delete a card by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted card"),
            @ApiResponse(code = 404, message = "Card not found")
    })
    // Delete a card
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
