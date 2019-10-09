package com.assignment.cardgame.models;

public class PlayerValueDescriptor {
    private int playerId;
    private int playerValue;

    public PlayerValueDescriptor(int playerId, int playerValue) {
        this.playerId = playerId;
        this.playerValue = playerValue;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPlayerValue() {
        return playerValue;
    }
}
