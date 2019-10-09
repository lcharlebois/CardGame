package com.assignment.cardgame.Unit.Services;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;
import com.assignment.cardgame.models.CardDescriptor;
import com.assignment.cardgame.models.Deck;
import com.assignment.cardgame.models.Game;
import com.assignment.cardgame.repositories.DeckRepository;
import com.assignment.cardgame.repositories.GameRepository;
import com.assignment.cardgame.services.EntityNotFoundException;
import com.assignment.cardgame.services.Dtos.GameDto;
import com.assignment.cardgame.services.GameManagementService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;
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

        GameDto gameDto = this.gameManagementService.addDeckToGame(game.getId(), deck.getId());
        Assert.assertEquals(gameDto.cards.size(), deck.getCards().size());
    }

    @Test()
    public void testAddPlayer() throws EntityNotFoundException, ValidationException {
        Game game = new Game();

        Mockito.when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        GameDto gameDto = this.gameManagementService.addPlayerToGame(game.getId(), 1);
        Assert.assertEquals(1, gameDto.players.size());
        Assert.assertEquals(1, gameDto.players.get(0).getId());
    }

    @Test()
    public void testDealCardToPlayer() throws EntityNotFoundException, ValidationException {
        Game game = new Game();
        int playerId = 1;
        game.addToGameDeck(Arrays.asList(new CardDescriptor(Face.EIGHT, Suit.HEARTS)));
        Mockito.when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        this.gameManagementService.addPlayerToGame(game.getId(), playerId);
        this.gameManagementService.dealCardToPlayer(game.getId(), playerId, 2);

        Assert.assertEquals(0, game.getGameDeck().size());
        Assert.assertEquals(Face.EIGHT, game.getPlayerList().get(0).getCards().get(0).getFace());
        Assert.assertEquals(Suit.HEARTS, game.getPlayerList().get(0).getCards().get(0).getSuit());
    }
}