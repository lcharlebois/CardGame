package com.assignment.cardgame.Unit.Controllers;

import com.assignment.cardgame.controllers.DeckController;
import com.assignment.cardgame.models.Deck;
import com.assignment.cardgame.repositories.DeckRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DeckController.class)
public class DeckControllerTests {

    @MockBean
    DeckRepository deckRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testFindAll() throws Exception {
        Deck deck = new Deck();

        List<Deck> decks = Arrays.asList(deck);

        Mockito.when(deckRepository.findAll()).thenReturn(decks);

        mockMvc.perform(get("/decks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(deck.getId())))
                .andExpect(jsonPath("$[0].cards", Matchers.hasSize(52)));
    }
}
