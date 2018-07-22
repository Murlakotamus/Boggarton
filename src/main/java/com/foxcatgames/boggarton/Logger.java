package com.foxcatgames.boggarton;

public final class Logger {
    public static final boolean IS_ACTIVE = false;

    private Logger(){
    }

    public static void log(final Object obj) {
        if (IS_ACTIVE)
            System.out.println(obj);
    }

    public static void err(final String string) {
        System.err.println(string);
    }
}
