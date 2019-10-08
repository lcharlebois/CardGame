package com.assignment.cardgame.Unit.Model;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;
import com.assignment.cardgame.models.CardDescriptor;
import com.assignment.cardgame.models.Deck;
import com.assignment.cardgame.models.Game;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GameTests {

    @Test
    public void testCreateGame(){
        Game game = new Game();
        Assert.assertEquals(0, game.getId());
        Assert.assertNotNull(game);
    }

    @Test
    public void testShuffleDeck() throws InterruptedException {
        Game game = new Game();
        List<CardDescriptor> cards = new Deck().GetCards();
        game.addToGameDeck(cards);

        game.shuffleGameDeck();
        List<CardDescriptor> shuffledCards = game.getGameDeck();
        Assert.assertFalse(shuffledCards.get(0).toString().equalsIgnoreCase(cards.get(0).toString()));
        Assert.assertFalse(shuffledCards.get(1).toString().equalsIgnoreCase(cards.get(1).toString()));
        Assert.assertFalse(shuffledCards.get(2).toString().equalsIgnoreCase(cards.get(2).toString()));
        Assert.assertFalse(shuffledCards.get(cards.size() -1).toString().equalsIgnoreCase(cards.get(cards.size() -1).toString()));

        game.shuffleGameDeck();
        List<CardDescriptor> veryShuffledCards = game.getGameDeck();
        Assert.assertFalse(veryShuffledCards.get(0).toString().equalsIgnoreCase(cards.get(0).toString()));
        Assert.assertFalse(veryShuffledCards.get(0).toString().equalsIgnoreCase(shuffledCards.get(0).toString()));
    }

    @Test
    public void testAddCards() throws InterruptedException {
        Game game = new Game();
        List<CardDescriptor> cards = new Deck().GetCards();
        Assert.assertEquals(0, game.getGameDeck().size());
        game.addToGameDeck(cards);
        Assert.assertEquals(cards.size(), game.getGameDeck().size());
    }

    @Test
    public void testPickCard(){
        Game game = new Game();
        game.addToGameDeck(Arrays.asList(new CardDescriptor(Face.EIGHT, Suit.SPADES)));
        CardDescriptor card = game.PickCard();

        Assert.assertEquals(0, game.getGameDeck ().size());
        Assert.assertNotNull(card.toString());
    }
    
    @Test
    public void testNoCardToPick(){
        Game game = new Game();
        CardDescriptor card = game.PickCard();

        Assert.assertNull(card);
    }
}
