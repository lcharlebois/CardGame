package com.assignment.cardgame.services;

import java.util.ArrayList;
import java.util.List;

public class GameDto {

    public int id;

    public List<CardDto> Cards = new ArrayList();

    protected GameDto() {
    }

    public GameDto(int id, List<CardDto> cards) {
        this.id = id;
        Cards = cards;
    }
}
