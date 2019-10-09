package com.assignment.cardgame.services.Dtos;

import java.util.List;

public class DeckDto {
    public int id;
    public List<String> Cards;

    protected DeckDto() {
    }

    public DeckDto(int id, List<String> cards) {
        this.id = id;
        this.Cards = cards;
    }
}
