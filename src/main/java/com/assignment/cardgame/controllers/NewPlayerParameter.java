package com.assignment.cardgame.controllers;

public class NewPlayerParameter {
    private int id;

    protected NewPlayerParameter() {
    }

    public NewPlayerParameter(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
