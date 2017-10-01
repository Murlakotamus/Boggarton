package stackup.game.utils;

import stackup.Const;

public class Utils {

    static public int random(final int number) {
        return (int) (Math.random() * number);
    }

    static public int randomBrick(int setSize) {
        return random(setSize) + Const.CURRENT_SET * 10 + 1;
    }

    static public void printStackTrace(StackTraceElement... elements) {
        for (StackTraceElement element : elements)
            System.err.println(element);
    }
}
