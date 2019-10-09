package com.assignment.cardgame.services.Dtos;

public class CardsPerSuitDto {
    private int heartsCount;
    private int spadesCount;
    private int clubsCount;
    private int diamondsCount;

    public CardsPerSuitDto() {
    }

    public CardsPerSuitDto(int heartsCount, int spadesCount, int clubsCount, int diamondsCount) {
        this.heartsCount = heartsCount;
        this.spadesCount = spadesCount;
        this.clubsCount = clubsCount;
        this.diamondsCount = diamondsCount;
    }

    public int getHeartsCount() {
        return this.heartsCount;
    }

    public int getSpadesCount() {
        return this.spadesCount;
    }

    public int getClubsCount() {
        return this.clubsCount;
    }

    public int getDiamondsCount() {
        return this.diamondsCount;
    }
}
