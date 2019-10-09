package com.assignment.cardgame.services.Dtos;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;

public class CardCountDto {
    private int count;
    private CardDto card;

    protected CardCountDto() {
    }

    public CardCountDto(CardDto card, int count) {
        this.count = count;
        this.card = card;
    }

    public int getCount() {
        return count;
    }

    public Suit getCardSuite() {
        return card.suit;
    }

    public Face getCardFace() {
        return card.face;
    }
}
