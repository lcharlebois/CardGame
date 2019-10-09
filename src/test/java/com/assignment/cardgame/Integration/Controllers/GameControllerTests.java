package com.assignment.cardgame.Integration.Controllers;

import com.assignment.cardgame.controllers.GameController;
import com.assignment.cardgame.services.Dtos.CardCountDto;
import com.assignment.cardgame.services.Dtos.CardsPerSuitDto;
import com.assignment.cardgame.services.Dtos.PlayerValueDto;
import com.assignment.cardgame.services.EntityNotFoundException;
import com.assignment.cardgame.services.Dtos.GameDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerTests {

    @Autowired
    GameController gameController;

    @Test
    public void testCreateGame(){
        // When
        GameDto game = gameController.create();

        // Then
        Assert.assertNotNull(game);
    }

    @Test
    public void testGetGames(){
        // Given
        GameDto createdGame1 = gameController.create();
        GameDto createdGame2 = gameController.create();

        // When
        List<GameDto> games = gameController.getAll();

        // Then
        Assert.assertNotNull(games);
        Assert.assertTrue(exists(games, createdGame1.id));
        Assert.assertTrue(exists(games, createdGame2.id));
    }

    @Test
    public void testDeleteGame() throws EntityNotFoundException {
        // Given
        GameDto createdGame = gameController.create();

        // When
        gameController.delete(createdGame.id);

        // Then
        List<GameDto> games = gameController.getAll();
        Assert.assertFalse(exists(games, createdGame.id));
    }

    @Test(expected = ValidationException.class)
    public void testGetUnknownPlayerTrowsWhenGettingCards() throws EntityNotFoundException, ValidationException {
        GameDto createdGame = gameController.create();

        gameController.getPlayerCards(createdGame.id, 3);
    }

    @Test()
    public void testDoesNotThrowWhenGettingCardAndDeckIsEmpty() throws EntityNotFoundException, ValidationException {
        GameDto createdGame = gameController.create();

        List<CardCountDto> cardCountDtos = gameController.getSortedCardCount(createdGame.id);
        CardsPerSuitDto cardsPerStuits = gameController.getCardsPerStuits(createdGame.id);

        Assert.assertEquals(0, cardCountDtos.size());
        Assert.assertEquals(0, cardsPerStuits.getSpadesCount());
        Assert.assertEquals(0, cardsPerStuits.getHeartsCount());
        Assert.assertEquals(0, cardsPerStuits.getDiamondsCount());
        Assert.assertEquals(0, cardsPerStuits.getClubsCount());
    }

    @Test()
    public void testNoPlayerDoesNotThrowWhenGettingValues() throws EntityNotFoundException, ValidationException {
        GameDto createdGame = gameController.create();

        List<PlayerValueDto> playerValues = gameController.getPlayerValues(createdGame.id);
        Assert.assertEquals(0, playerValues.size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUnknownGameWhenGettingPlayerCard() throws EntityNotFoundException, ValidationException {
        Random random = new Random();
        gameController.getPlayerCards(random.nextInt(99999), random.nextInt(99999));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUnknownGameWhenCardCount() throws EntityNotFoundException, ValidationException {
        Random random = new Random();
        gameController.getSortedCardCount(random.nextInt(99999));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUnknownGameWhenCardPerSuits() throws EntityNotFoundException, ValidationException {
        Random random = new Random();
        gameController.getCardsPerStuits(random.nextInt(99999));
    }

    private boolean exists(List<GameDto> games, int idToSearch){
        for (GameDto game : games) {
            if (game.id == idToSearch){
                return true;
            }
        }

        return false;
    }
}
