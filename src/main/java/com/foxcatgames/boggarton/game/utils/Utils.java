package com.foxcatgames.boggarton.game.utils;

import com.foxcatgames.boggarton.Const;

public class Utils {

    static public int random(final int number) {
        return (int) (Math.random() * number);
    }

    static int getBrickType(int i) {
        return i + Const.CURRENT_SET * 10 + 1;
    }

    static public int randomBrick(final int difficulty) {
        return getBrickType(random(difficulty));
    }

    static public int probabilisticBrick(final int difficulty, final int[] probabilities) {
        int total = 0;
        for (int i = 0; i < probabilities.length && i < difficulty; i++)
            total += probabilities[i];

        final double quantile = 1d / total;
        final double p = Math.random();

        double percenthile = 0;
        for (int i = 0; i < probabilities.length && i < difficulty; i++) {
            percenthile += (quantile * probabilities[i]);
            if (p <= percenthile)
                return getBrickType(i);
        }
        return getBrickType(difficulty);
    }

    static public int getBrick(final String str, final int index) {
        return Integer.parseInt("" + str.charAt(index)) + Const.CURRENT_SET * 10;
    }

}
