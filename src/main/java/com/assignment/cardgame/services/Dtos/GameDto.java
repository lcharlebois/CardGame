package com.assignment.cardgame.services.Dtos;

import java.util.ArrayList;
import java.util.List;

public class GameDto {

    public int id;

    public List<CardDto> cards = new ArrayList();

    public List<PlayerDto> players = new ArrayList();

    protected GameDto() {
    }

    public GameDto(int id, List<CardDto> cards, List<PlayerDto> players) {
        this.id = id;
        this.cards = cards;
        this.players = players;
    }
}
