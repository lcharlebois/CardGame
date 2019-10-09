package com.assignment.cardgame.Unit.Controllers;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;
import com.assignment.cardgame.controllers.GameController;
import com.assignment.cardgame.services.Dtos.CardDto;
import com.assignment.cardgame.services.Dtos.GameDto;
import com.assignment.cardgame.services.Dtos.PlayerDto;
import com.assignment.cardgame.services.EntityNotFoundException;
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

import javax.xml.bind.ValidationException;
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
        CardDto playerCard = new CardDto(Face.QUEEN, Suit.SPADES);
        CardDto deckCard = new CardDto(Face.ACE, Suit.CLUBS);

        PlayerDto playerDto = new PlayerDto(2, Arrays.asList(playerCard));

        GameDto game = new GameDto(
                1,
                Arrays.asList(deckCard),
                Arrays.asList(playerDto));

        List<GameDto> games = Arrays.asList(game);

        Mockito.when(gameManagementService.getAllGames()).thenReturn(games);

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].cards", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].players", Matchers.hasSize(1)));
    }
}
