package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;

abstract public class AbstractGameScene extends AbstractScene {

    protected static final int Y = 160;
    protected boolean escapePressed = false;

    AbstractGameScene(final SceneItem scene) {
        super(scene);
        addEscape();
        addPause();
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

    protected void setGameOver() {
        if (TIMER.isPaused())
            TIMER.resume();
    }
}
