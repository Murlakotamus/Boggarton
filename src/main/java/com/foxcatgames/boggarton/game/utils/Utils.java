package com.foxcatgames.boggarton.game.utils;

import static com.foxcatgames.boggarton.Const.CURRENT_SET;;

public class Utils {

    static public int getBrickType(final int i) {
        return i + CURRENT_SET * 10 + 1;
    }

    static public int random(final int number) {
        return (int) (Math.random() * number);
    }

    static public int getBrick(final int difficulty, final int[] probabilities) {
        int total = 0;
        for (int i = 0; i < difficulty; i++)
            total += probabilities[i];

        final double quantile = 1d / total;
        final double p = Math.random();

        double percenthile = 0;
        for (int i = 0; i < difficulty; i++) {
            percenthile += (quantile * probabilities[i]);
            if (p <= percenthile)
                return getBrickType(i);
        }
        return getBrickType(difficulty);
    }

    static public int parseBrick(final String str, final int index) {
        return Integer.parseInt("" + str.charAt(index)) + CURRENT_SET * 10;
    }

}
