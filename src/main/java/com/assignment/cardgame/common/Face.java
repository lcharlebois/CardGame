package com.assignment.cardgame.common;

import java.util.HashMap;
import java.util.Map;

public enum Face {
    ACE(0),
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4),
    SIX(5),
    SEVEN(6),
    EIGHT(7),
    NINE(8),
    TEN(9),
    JACK(10),
    QUEEN(11),
    KING(12);

    private final int value;
    private static Map map = new HashMap<>();

    static {
        for (Face face : Face.values()) {
            map.put(face.value, face);
        }
    }

    Face(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Face from(int value)
    {
        return (Face) map.get(value);
    }
}
