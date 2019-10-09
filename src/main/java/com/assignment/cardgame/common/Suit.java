package com.assignment.cardgame.common;

import java.util.HashMap;
import java.util.Map;

public enum Suit {
    DIAMONDS(0),
    CLUBS(1),
    SPADES(2),
    HEARTS(3);

    private final int value;
    private static Map map = new HashMap<>();

    static {
        for (Suit suit : Suit.values()) {
            map.put(suit.value, suit);
        }
    }

    Suit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Suit from(int value)
    {
        return (Suit) map.get(value);
    }
}
