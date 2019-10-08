package com.assignment.cardgame.Unit.Model;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;
import com.assignment.cardgame.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.EnumSet;
import java.util.List;

public class DeckTests {

    @Test
    public void testCreateDeck(){
        Deck deck = new Deck();
        Assert.assertEquals(0, deck.getId());
        Assert.assertEquals(52, deck.GetCards().size());
    }

    @Test
    public void testGetCards(){
        Deck deck = new Deck();
        List<CardDescriptor> cards = deck.GetCards();

        Assert.assertEquals(52, cards.size());
        for (Suit suit  : EnumSet.allOf(Suit.class)) {
            for (Face face  : EnumSet.allOf(Face.class)) {
                Assert.assertTrue(this.IsFound(cards, face, suit));
            }
        }
    }

    private boolean IsFound(List<CardDescriptor> cards, Face faceToFind, Suit suitToFind){
        for (CardDescriptor card : cards) {
            if (card.equals(new CardDescriptor(faceToFind, suitToFind)))
                return true;
        }

        return false;
    }
}
