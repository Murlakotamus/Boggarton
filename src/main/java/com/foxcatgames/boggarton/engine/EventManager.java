package com.foxcatgames.boggarton.engine;

import java.util.ArrayList;
import java.util.List;

public final class EventManager {

    private static EventManager instance = new EventManager();
    private static List<KeyListener> listeners = new ArrayList<KeyListener>();

    private EventManager() {
    }

    public static EventManager getInstance() {
        return instance;
    }

    public void addListener(final int key, final KeyListener listener) {
        listener.setKeyMonitored(key);
        listeners.add(listener);
    }

    public void removeListener(final KeyListener listener) {
        listeners.remove(listener);
    }

    public void clear() {
        listeners.clear();
    }

    public void checkEvents() {
        for (final KeyListener listener : listeners) {
            listener.checkKey();
        }
    }
}
