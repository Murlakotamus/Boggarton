package com.foxcatgames.boggarton.game.utils;

public class Victories {

    private static final int[] victories = { 0, 0 };

    public static void addVictory(int i) {
        victories[i]++;
    }

    public static int getVictories(int i) {
        return victories[i];
    }
}
