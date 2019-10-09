package com.assignment.cardgame.models;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;

public class CardCount {
    private int count;
    private CardDescriptor card;

    public CardCount(CardDescriptor card, int count) {
        this.count = count;
        this.card = card;
    }

    public CardDescriptor getCard() {
        return card;
    }

    public int getCount() {
        return count;
    }

    public Suit getCardSuite() {
        return card.getSuit();
    }

    public Face getCardFace() {
        return card.getFace();
    }
}
