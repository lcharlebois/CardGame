package com.assignment.cardgame.Unit.Services;

import com.assignment.cardgame.models.Deck;
import com.assignment.cardgame.models.Game;
import com.assignment.cardgame.repositories.DeckRepository;
import com.assignment.cardgame.repositories.GameRepository;
import com.assignment.cardgame.services.EntityNotFoundException;
import com.assignment.cardgame.services.GameDto;
import com.assignment.cardgame.services.GameManagementService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameManagementServiceTests {

    @MockBean
    GameRepository gameRepository;

    @MockBean
    DeckRepository deckRepository;

    @Autowired
    GameManagementService gameManagementService;

    @Test
    public void testGetAll() {
        Game game = new Game();

        Mockito.when(gameRepository.findAll()).thenReturn(Arrays.asList(game));

        List<GameDto> games = gameManagementService.getAllGames();
        Assert.assertEquals(1, games.size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDelete() throws EntityNotFoundException {
        gameManagementService.deleteGame(0);
    }

    @Test()
    public void testCreate() {
        Mockito.when(gameRepository.save(any())).thenReturn(new Game());
        GameDto gameDto = this.gameManagementService.createGame();
        Assert.assertNotNull(gameDto);
    }

    @Test()
    public void testAddCard() throws EntityNotFoundException {
        Game game = new Game();
        Deck deck = new Deck();

        Mockito.when(deckRepository.findById(deck.getId())).thenReturn(Optional.of(deck));
        Mockito.when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        GameDto gameDto = this.gameManagementService.AddDeckToGame(game.getId(), deck.getId());
        Assert.assertEquals(gameDto.Cards.size(), deck.GetCards().size());
    }
}