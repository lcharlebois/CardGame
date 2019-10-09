package com.assignment.cardgame.services.Dtos;

public class PlayerValueDto {
    private int playerId;
    private int playerValue;

    protected PlayerValueDto() {
    }

    public PlayerValueDto(int playerId, int playerValue) {
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
