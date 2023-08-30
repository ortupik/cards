package com.logicea.cards.controller;

import com.logicea.cards.service.CardService;
import com.logicea.cards.controller.CardController;
import com.logicea.cards.dto.CardRequest;
import com.logicea.cards.model.CardEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CardControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CardController cardController;

    @Mock
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    public void testCreateCard() throws Exception {
        CardRequest request = new CardRequest();
        request.setName("Test Card");
        CardEntity card = new CardEntity();
        card.setName(request.getName());
        when(cardService.createCard(request, "test@example.com")).thenReturn(card);

        mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Test Card\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Card")));

        // ... Add more tests for other endpoints ...
    }

     @Test
    public void testGetCardById() throws Exception {
        CardEntity card = new CardEntity();
        card.setId(1L);
        card.setName("Test Card");

        when(cardService.getCardById(1L)).thenReturn(card);

        mockMvc.perform(get("/api/cards/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Card")));
    }
     @Test
    public void testUpdateCard() throws Exception {
        CardRequest request = new CardRequest();
        request.setName("Updated Card");

        mockMvc.perform(put("/api/cards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Updated Card\" }"))
                .andExpect(status().isOk());

        verify(cardService, times(1)).updateCard(eq(1L), any(CardRequest.class));
    }
}