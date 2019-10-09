package com.assignment.cardgame.models;

public class SuitCounts {
    private int HeartsCount;
    private int SpadesCount;
    private int ClubsCount;
    private int DiamondsCount;

    public SuitCounts(int heartsCount, int spadesCount, int clubsCount, int diamondsCount) {
        HeartsCount = heartsCount;
        SpadesCount = spadesCount;
        ClubsCount = clubsCount;
        DiamondsCount = diamondsCount;
    }

    public int getHeartsCount() {
        return HeartsCount;
    }

    public int getSpadesCount() {
        return SpadesCount;
    }

    public int getClubsCount() {
        return ClubsCount;
    }

    public int getDiamondsCount() {
        return DiamondsCount;
    }
}
