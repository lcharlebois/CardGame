package com.assignment.cardgame.Unit.Controllers;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;
import com.assignment.cardgame.controllers.GameController;
import com.assignment.cardgame.models.Game;
import com.assignment.cardgame.repositories.GameRepository;
import com.assignment.cardgame.services.CardDto;
import com.assignment.cardgame.services.GameDto;
import com.assignment.cardgame.services.GameManagementService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTests {

    @MockBean
    GameManagementService gameManagementService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testFindAll() throws Exception {
        GameDto game = new GameDto(1, Arrays.asList(new CardDto(Face.ACE, Suit.CLUBS)));

        List<GameDto> games = Arrays.asList(game);

        Mockito.when(gameManagementService.getAllGames()).thenReturn(games);

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(game.id)));
    }
}
