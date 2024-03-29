package com.assignment.cardgame.models;

public class SuitCounts {
    private int heartsCount;
    private int spadesCount;
    private int clubsCount;
    private int diamondsCount;

    public SuitCounts(int heartsCount, int spadesCount, int clubsCount, int diamondsCount) {
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
