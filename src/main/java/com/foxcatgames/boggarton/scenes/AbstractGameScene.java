package com.foxcatgames.boggarton.scenes;

import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;

abstract public class AbstractGameScene extends AbstractScene {

    protected static final int Y = 160;
    protected boolean escapePressed = false;

    AbstractGameScene(final SceneItem scene) {
        super(scene);
        addEscape();
        addPause();
        addStartStackTrace();
    }

    abstract protected void hideGlass();
    abstract protected void showGlass();

    private void addEscape() {
        final KeyListener escape = new KeyListener() {
            @Override
            public void onKeyUp() {
                escapePressed = true;
                setGameOver();
                nextScene(SceneItem.MENU);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }

    private void addPause() {
        final KeyListener pause = new KeyListener() {
            @Override
            public void onKeyDown() {
                if (TIMER.isPaused()) {
                    TIMER.resume();
                    showGlass();
                } else {
                    TIMER.pause();
                    hideGlass();
                }
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_P, pause);
    }

    private void addStartStackTrace() {
        final KeyListener stackTrace = new KeyListener() {
            @Override
            public void onKeyUp() {
                startTrace();
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_T, stackTrace);
    }

    protected void setGameOver() {
        if (TIMER.isPaused())
            TIMER.resume();
    }

    private void startTrace() {
        final Thread watcher = new Thread("watcher") {
            @Override
            public void run() {
                for (;;)
                    try {
                        final Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
                        for (final Map.Entry<Thread, StackTraceElement[]> elements : map
                                .entrySet()) {
                            for (StackTraceElement element : elements.getValue())
                                Logger.err(elements.getKey().getName() + ": " + element);
                            Logger.err("-----------------------------------------------");
                        }
                        Logger.err("===================================================");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
            }
        };
        watcher.setDaemon(true);
        watcher.start();
    }
}
