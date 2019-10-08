package com.assignment.cardgame.Integration.Controllers;

import com.assignment.cardgame.controllers.GameController;
import com.assignment.cardgame.models.CardDescriptor;
import com.assignment.cardgame.models.Deck;
import com.assignment.cardgame.services.DeckDto;
import com.assignment.cardgame.services.EntityNotFoundException;
import com.assignment.cardgame.services.GameDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

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

//
//    @Test
//    public void testDealCardDeck(){ // todo fix it
//        // Given
//        GameDto createdGame = gameController.create();
//
//        // When
//        String card = (String)gameController(createdGame.id).getBody();
//
//        // Then
//        DeckDto deck = (DeckDto)gameController.get(createdGame.id).getBody();
//        Assert.assertEquals(51, deck.Cards.size());
//        Assert.assertNotNull(card);
//        Assert.assertNotNull(card);
//    }

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

    private boolean exists(List<GameDto> games, int idToSearch){
        for (GameDto game : games) {
            if (game.id == idToSearch){
                return true;
            }
        }

        return false;
    }
}
