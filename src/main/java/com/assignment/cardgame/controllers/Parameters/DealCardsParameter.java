package com.assignment.cardgame.controllers.Parameters;

public class DealCardsParameter {
    private int numberOfCards;

    public DealCardsParameter(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public DealCardsParameter() {
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }
}
