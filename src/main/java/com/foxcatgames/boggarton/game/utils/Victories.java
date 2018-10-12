package com.foxcatgames.boggarton.game.utils;

public class Victories {

    private static final int[] VICTORY = { 0, 0 };

    public static void addVictory(final int i) {
        VICTORY[i]++;
    }

    public static int getVictories(final int i) {
        return VICTORY[i];
    }
}
