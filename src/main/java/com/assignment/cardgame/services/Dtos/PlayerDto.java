package com.assignment.cardgame.services.Dtos;

import java.util.ArrayList;
import java.util.List;

public class PlayerDto {

    int id;

    List<CardDto> cards = new ArrayList();

    public PlayerDto(int id, List<CardDto> cards) {
        this.id = id;
        this.cards = cards;
    }

    protected PlayerDto() {
    }

    public int getId() {
        return id;
    }

    public List<CardDto> getCards() {
        return cards;
    }
}
