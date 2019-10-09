package com.assignment.cardgame.models;

import java.util.List;

public class PlayerDescriptor {
    private int id;

    private List<CardDescriptor> cards;

    public PlayerDescriptor(int id, List<CardDescriptor> cards) {
        this.id = id;
        this.cards = cards;
    }

    public List<CardDescriptor> getCards() {
        return cards;
    }

    public int getId() {
        return id;
    }
}
