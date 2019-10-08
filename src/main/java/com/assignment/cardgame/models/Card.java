package com.assignment.cardgame.models;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;

import javax.persistence.*;

@Embeddable
public class Card {
    @Enumerated(EnumType.STRING)
    Face face;

    @Enumerated(EnumType.STRING)
    Suit suit;

    protected Card() {
    }

    public Card(Face face, Suit suit) {
        this.face = face;
        this.suit = suit;
    }

    public Face getFace() {
        return face;
    }

    public Suit getSuit() {
        return suit;
    }
}
