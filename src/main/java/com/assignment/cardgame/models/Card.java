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

    public int getCardValue(){
        int x = suit.getValue() * 13;
        return x + face.getValue();
    }

    public static Card fromCardValue(int cardValue){
        int face = cardValue % 13;
        int suit = cardValue / 13;

        return new Card(Face.from(face), Suit.from(suit));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

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
