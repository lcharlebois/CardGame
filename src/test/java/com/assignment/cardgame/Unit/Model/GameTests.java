package com.assignment.cardgame.Unit.Model;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;
import com.assignment.cardgame.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.xml.bind.ValidationException;
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
        List<CardDescriptor> cards = new Deck().getCards();
        game.addToGameDeck(cards);

        game.shuffleGameDeck();
        List<CardDescriptor> shuffledCards = game.getGameDeck();
        Assert.assertFalse(shuffledCards.get(0).toString().equalsIgnoreCase(cards.get(0).toString()));
        Assert.assertFalse(shuffledCards.get(1).toString().equalsIgnoreCase(cards.get(1).toString()));
        Assert.assertFalse(shuffledCards.get(2).toString().equalsIgnoreCase(cards.get(2).toString()));
        Assert.assertFalse(shuffledCards.get(cards.size() -1).toString().equalsIgnoreCase(cards.get(cards.size() -1).toString()));

        // Force a new seed for the random number generator to avoid the test being flaky
        Thread.sleep(1);

        game.shuffleGameDeck();
        List<CardDescriptor> veryShuffledCards = game.getGameDeck();
        Assert.assertFalse(veryShuffledCards.get(0).toString().equalsIgnoreCase(cards.get(0).toString()));
        Assert.assertFalse(veryShuffledCards.get(0).toString().equalsIgnoreCase(shuffledCards.get(0).toString()));
    }

    @Test
    public void testAddCards()  {
        Game game = new Game();
        List<CardDescriptor> cards = new Deck().getCards();
        Assert.assertEquals(0, game.getGameDeck().size());
        game.addToGameDeck(cards);
        Assert.assertEquals(cards.size(), game.getGameDeck().size());
    }

    @Test
    public void testAddPlayer()  {
        Game game = new Game();
        game.addPlayer(new Player(5));
        Assert.assertEquals(1, game.getPlayerList().size());
        Assert.assertEquals(5, game.getPlayerList().get(0).getId());
    }

    @Test
    public void testRemovePlayer() throws ValidationException {
        Game game = new Game();
        game.addPlayer(new Player(5));
        game.removePlayer(5);
        Assert.assertEquals(0, game.getPlayerList().size());
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

    @Test
    public void testGetSuitsCount(){
        Game game = new Game();
        CardDescriptor card1 = new CardDescriptor(Face.ACE, Suit.SPADES);
        CardDescriptor card2 = new CardDescriptor(Face.JACK, Suit.SPADES);
        CardDescriptor card3 = new CardDescriptor(Face.EIGHT, Suit.HEARTS);
        CardDescriptor card4 = new CardDescriptor(Face.NINE, Suit.CLUBS);
        CardDescriptor card5 = new CardDescriptor(Face.QUEEN, Suit.DIAMONDS);
        CardDescriptor card6 = new CardDescriptor(Face.KING, Suit.DIAMONDS);

        game.addToGameDeck(Arrays.asList(card1, card2, card3, card4, card5, card6));

        SuitCounts suitCounts = game.getSuitCounts();

        Assert.assertEquals(2, suitCounts.getSpadesCount());
        Assert.assertEquals(2, suitCounts.getSpadesCount());
        Assert.assertEquals(1, suitCounts.getHeartsCount());
        Assert.assertEquals(1, suitCounts.getClubsCount());
    }

    @Test
    public void testGetSortedCardCount(){
        Game game = new Game();
        CardDescriptor card1 = new CardDescriptor(Face.ACE, Suit.SPADES);
        CardDescriptor card2 = new CardDescriptor(Face.TWO, Suit.SPADES);
        CardDescriptor card3 = new CardDescriptor(Face.JACK, Suit.SPADES);
        CardDescriptor card4 = new CardDescriptor(Face.EIGHT, Suit.HEARTS);
        CardDescriptor card5 = new CardDescriptor(Face.SEVEN, Suit.HEARTS);
        CardDescriptor card6 = new CardDescriptor(Face.NINE, Suit.CLUBS);
        CardDescriptor card7 = new CardDescriptor(Face.NINE, Suit.CLUBS);
        CardDescriptor card8 = new CardDescriptor(Face.NINE, Suit.CLUBS);
        CardDescriptor card9 = new CardDescriptor(Face.QUEEN, Suit.DIAMONDS);
        CardDescriptor card10 = new CardDescriptor(Face.KING, Suit.DIAMONDS);

        List<CardDescriptor> cardDescriptors = Arrays.asList(
                card1,
                card2,
                card3,
                card4,
                card5,
                card6,
                card7,
                card8,
                card9,
                card10);

        game.addToGameDeck(cardDescriptors);

        List<CardCount> sortedCardCount = game.getSortedCardCount();

        ValidateOrder(sortedCardCount, 0, Suit.HEARTS, Face.EIGHT, 1);
        ValidateOrder(sortedCardCount, 1, Suit.HEARTS, Face.SEVEN, 1);
        ValidateOrder(sortedCardCount, 2, Suit.SPADES, Face.JACK, 1);
        ValidateOrder(sortedCardCount, 3, Suit.SPADES, Face.TWO, 1);
        ValidateOrder(sortedCardCount, 4, Suit.SPADES, Face.ACE, 1);
        ValidateOrder(sortedCardCount, 5, Suit.CLUBS, Face.NINE, 3);
        ValidateOrder(sortedCardCount, 6, Suit.DIAMONDS, Face.KING, 1);
        ValidateOrder(sortedCardCount, 7, Suit.DIAMONDS, Face.QUEEN, 1);
    }

    @Test()
    public void testDealCardToPlayer() throws ValidationException {
        Game game = new Game();
        CardDescriptor card1 = new CardDescriptor(Face.ACE, Suit.SPADES);
        CardDescriptor card2 = new CardDescriptor(Face.TWO, Suit.SPADES);
        CardDescriptor card3 = new CardDescriptor(Face.JACK, Suit.HEARTS);

        game.addToGameDeck(Arrays.asList(card1, card2, card3));

        game.addPlayer(new Player(4));
        game.dealCardToPlayer(4, 2);

        Assert.assertEquals(1, game.getGameDeck().size());
        Assert.assertEquals(4, game.getPlayerList().get(0).getId());
        Assert.assertEquals(2, game.getPlayerList().get(0).getCards().size());
    }

    @Test()
    public void testNoMoreCardToDealToPlayer() throws ValidationException {
        Game game = new Game();
        CardDescriptor card1 = new CardDescriptor(Face.ACE, Suit.SPADES);

        game.addToGameDeck(Arrays.asList(card1));

        game.addPlayer(new Player(4));
        game.dealCardToPlayer(4, 2);

        Assert.assertEquals(0, game.getGameDeck().size());
        Assert.assertEquals(4, game.getPlayerList().get(0).getId());
        Assert.assertEquals(1, game.getPlayerList().get(0).getCards().size());
    }

    @Test(expected = ValidationException.class)
    public void testDealCardToPlayerNotFound() throws ValidationException {
        Game game = new Game();
        CardDescriptor card1 = new CardDescriptor(Face.ACE, Suit.SPADES);
        CardDescriptor card2 = new CardDescriptor(Face.TWO, Suit.SPADES);

        game.addToGameDeck(Arrays.asList(card1, card2));

        game.dealCardToPlayer(4, 2);
    }

    private void ValidateOrder(List<CardCount> sortedCardCount, int index, Suit suit, Face face, int count) {
        Assert.assertEquals(suit, sortedCardCount.get(index).getCardSuite());
        Assert.assertEquals(face, sortedCardCount.get(index).getCardFace());
        Assert.assertEquals(count, sortedCardCount.get(index).getCount());
    }
}
