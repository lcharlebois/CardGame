package com.assignment.cardgame.models;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;

public class CardDescriptor {
    private Face face;

    private Suit suit;

    public CardDescriptor(Face face, Suit suit) {
        this.face = face;
        this.suit = suit;
    }

    public Face getFace() {
        return face;
    }

    public Suit getSuit() {
        return suit;
    }


    @Override
    public String toString() {
        return this.face.name() + " of " + this.suit.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardDescriptor card = (CardDescriptor) o;

        if (face != card.face) return false;
        return suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = face != null ? face.hashCode() : 0;
        result = 31 * result + (suit != null ? suit.hashCode() : 0);
        return result;
    }
}
