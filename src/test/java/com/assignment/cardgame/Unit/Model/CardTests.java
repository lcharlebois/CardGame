package com.assignment.cardgame.Unit.Model;

import com.assignment.cardgame.models.Card;
import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;
import org.junit.Assert;
import org.junit.Test;

public class CardTests {

    @Test
    public void testCreateCard(){
        Card card = new Card(Face.ACE, Suit.CLUBS);
        Assert.assertNotNull(card);
        Assert.assertEquals(Face.ACE, card.getFace());
        Assert.assertEquals(Suit.CLUBS, card.getSuit());
    }

    @Test
    public void testCreateCardFromValue(){
        Card card = Card.fromCardValue(40);
        Assert.assertNotNull(card);
        Assert.assertEquals(Face.TWO, card.getFace());
        Assert.assertEquals(Suit.HEARTS, card.getSuit());
    }

    @Test
    public void testGetCardValue(){
        Card card = new Card(Face.ACE, Suit.CLUBS);
        int value = card.getCardValue();
        Assert.assertNotNull(card);
        Assert.assertEquals(13, value);
    }
}
