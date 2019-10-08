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
}
