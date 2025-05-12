package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;

abstract public class AbstractPlayingScene extends AbstractScene {

    protected static final int Y = 160;
    protected boolean escapePressed;
    protected boolean gameOver;

    AbstractPlayingScene(final SceneItem scene) {
        super(scene);
        addKeyEscape(SceneItem.MENU);
        addKeyPause();
        addKeyMute();
    }

    abstract protected void hideGlass();

    abstract protected void showGlass();

    @Override
    protected void addKeyEscape(final SceneItem sceneItem) {
        final KeyListener escape = new KeyListener() {
            @Override
            public void onKeyUp() {
                escapePressed = true;
                setGameOver();
                nextScene(sceneItem);
            }
        };
        EventManager.addListener(Keyboard.KEY_ESCAPE, escape);
    }

    private void addKeyPause() {
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
        EventManager.addListener(Keyboard.KEY_P, pause);
    }

    protected void addKeyMute() {
        final KeyListener escape = new KeyListener() {
            @Override
            public void onKeyUp() {
                SceneItem.soundType = SceneItem.soundType.relative(1);
            }
        };
        EventManager.addListener(Keyboard.KEY_M, escape);
    }

    protected void setGameOver() {
        if (TIMER.isPaused())
            TIMER.resume();
    }
}
