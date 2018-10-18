package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;

abstract public class AbstractPlayingScene<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>> extends AbstractScene {

    protected static final int Y = 160;
    protected boolean escapePressed = false;
    protected boolean gameOver = false;

    AbstractPlayingScene(final SceneItem scene) {
        super(scene);
        addKeyEscape(SceneItem.MENU);
        addKeyPause();
    }

    abstract protected void hideGlass();

    abstract protected void showGlass();

    protected void addKeyEscape(final SceneItem sceneItem) {
        final KeyListener escape = new KeyListener() {
            @Override
            public void onKeyUp() {
                escapePressed = true;
                setGameOver();
                nextScene(sceneItem);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
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
        EventManager.getInstance().addListener(Keyboard.KEY_P, pause);
    }

    protected void setGameOver() {
        if (TIMER.isPaused())
            TIMER.resume();
    }

    protected int getFigureSize(final AbstractVisualGame<B, F, G, P> game) {
        return game.getGlass().figure().getLenght();
    }
}
