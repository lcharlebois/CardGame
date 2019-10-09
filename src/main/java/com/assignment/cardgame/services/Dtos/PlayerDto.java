package com.assignment.cardgame.services.Dtos;

import com.assignment.cardgame.models.CardDescriptor;

import java.util.ArrayList;
import java.util.List;

public class PlayerDto {

    int id;

    List<CardDto> Cards = new ArrayList();

    public PlayerDto(int id, List<CardDto> cards) {
        this.id = id;
        Cards = cards;
    }

    protected PlayerDto() {
    }

    public int getId() {
        return id;
    }

    public List<CardDto> getCards() {
        return Cards;
    }
}
