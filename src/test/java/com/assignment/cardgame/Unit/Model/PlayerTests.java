package com.assignment.cardgame.Unit.Model;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;
import com.assignment.cardgame.models.CardDescriptor;
import com.assignment.cardgame.models.Player;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTests {

    @Test
    public void testCreatePlayer(){
        Player player = new Player(0);
        Assert.assertEquals(0, player.getId());
        Assert.assertNotNull(player);
    }

    @Test
    public void testAddAndGetCards() {
        Player player = new Player(0);
        player.addCard(new CardDescriptor(Face.QUEEN, Suit.DIAMONDS));
        Assert.assertEquals(1, player.getPlayerCards().size());
        Assert.assertEquals("QUEEN of DIAMONDS", player.getPlayerCards().get(0).toString());
    }

    @Test
    public void testTotalCardsValue() {
        Player player = new Player(0);
        player.addCard(new CardDescriptor(Face.ACE, Suit.DIAMONDS));
        player.addCard(new CardDescriptor(Face.KING, Suit.DIAMONDS));

        Assert.assertEquals(14, player.GetCardsValue());
    }
}
