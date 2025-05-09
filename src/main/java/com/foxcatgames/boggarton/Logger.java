package com.foxcatgames.boggarton;

public final class Logger {
    public static final boolean IS_ACTIVE = false;

    public static void log(final Object obj) {
        if (IS_ACTIVE)
            System.out.println(obj);
    }

    public static void err(final String string) {
        System.err.println(string);
    }

    public static void debug(final String string) {
        System.out.println(string);
    }

    public static void printStackTrace(final Exception e) {
        e.printStackTrace();
    }
}
