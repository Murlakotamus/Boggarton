package com.foxcatgames.boggarton.engine;

import java.util.ArrayList;
import java.util.List;

public final class EventManager {

    private static final EventManager INST = new EventManager();
    private static final List<KeyListener> LISTENERS = new ArrayList<KeyListener>();

    private EventManager() {
    }

    public static EventManager getInstance() {
        return INST;
    }

    public void addListener(final int key, final KeyListener listener) {
        listener.setKeyMonitored(key);
        LISTENERS.add(listener);
    }

    public void removeListener(final KeyListener listener) {
        LISTENERS.remove(listener);
    }

    public void clear() {
        LISTENERS.clear();
    }

    public void checkEvents() {
        for (final KeyListener listener : LISTENERS) {
            listener.checkKey();
        }
    }
}
