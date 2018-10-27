package com.foxcatgames.boggarton.engine;

import java.util.ArrayList;
import java.util.List;

public final class EventManager {

    private static final List<KeyListener> LISTENERS = new ArrayList<>();

    public static void addListener(final int key, final KeyListener listener) {
        listener.setKeyMonitored(key);
        LISTENERS.add(listener);
    }

    public static void removeListener(final KeyListener listener) {
        LISTENERS.remove(listener);
    }

    public static void clear() {
        LISTENERS.clear();
    }

    public static void checkEvents() {
        for (final KeyListener listener : LISTENERS) {
            listener.checkKey();
        }
    }
}
