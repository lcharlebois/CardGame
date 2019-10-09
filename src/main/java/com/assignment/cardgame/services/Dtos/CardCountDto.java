package com.assignment.cardgame.services.Dtos;

public class CardCountDto {
    public int count;
    public CardDto card;

    public CardCountDto() {
    }

    public CardCountDto(CardDto card, int count) {
        this.count = count;
        this.card = card;
    }
}
