package com.assignment.cardgame.services;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;

public class CardDto {
    public Face face;
    public Suit suit;

    protected CardDto() {
    }

    public CardDto(Face face, Suit suit) {
        this.face = face;
        this.suit = suit;
    }
}
