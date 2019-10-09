package com.assignment.cardgame.Integration.Controllers;

import com.assignment.cardgame.controllers.DeckController;
import com.assignment.cardgame.services.Dtos.DeckDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeckControllerTests {

    @Autowired
    DeckController deckController;

    @Test
    public void testCreateDeck(){
        // When
        DeckDto deck = deckController.create();

        // Then
        Assert.assertNotNull(deck.id);
        Assert.assertNotNull(deck.Cards);
        Assert.assertEquals(52, deck.Cards.size());
        Assert.assertTrue(deck.Cards.contains("ACE of SPADES"));
        Assert.assertTrue(deck.Cards.contains("QUEEN of DIAMONDS"));
    }

    @Test
    public void testGetDecks(){
        // Given
        DeckDto createdDeck1 = deckController.create();
        DeckDto createdDeck2 = deckController.create();

        // When
        List<DeckDto> decks = deckController.read();

        // Then
        Assert.assertNotNull(decks);
        Assert.assertTrue(exists(decks, createdDeck1.id));
        Assert.assertTrue(exists(decks, createdDeck2.id));
    }

    @Test
    public void testGetDeck(){
        // Given
        DeckDto createdDeck = deckController.create();

        // When
        DeckDto deck = (DeckDto)deckController.get(createdDeck.id).getBody();

        // Then
        Assert.assertNotNull(deck);
        Assert.assertNotNull(deck.Cards);
    }

    @Test
    public void testDeleteDeck(){
        // Given
        DeckDto createdDeck = deckController.create();


        // When
        deckController.delete(createdDeck.id);

        // Then
        List<DeckDto> decks = deckController.read();
        Assert.assertFalse(exists(decks, createdDeck.id));
    }

    private boolean exists(List<DeckDto> decks, int idToSearch){
        for (DeckDto deck : decks) {
            if (deck.id == idToSearch){
                return true;
            }
        }

        return false;
    }
}
