package com.foxcatgames.boggarton.game.utils;

import com.foxcatgames.boggarton.Const;

public class Utils {

    static public int random(final int number) {
        return (int) (Math.random() * number);
    }

    static public int randomBrick(final int difficulty) {
        return random(difficulty) + Const.CURRENT_SET * 10 + 1;
    }

    static public void printStackTrace(StackTraceElement... elements) {
        for (StackTraceElement element : elements)
            System.err.println(element);
    }

    static public int getBrick(final String str, final int index) {
        return Integer.parseInt("" + str.charAt(index)) + Const.CURRENT_SET * 10;
    }
}
