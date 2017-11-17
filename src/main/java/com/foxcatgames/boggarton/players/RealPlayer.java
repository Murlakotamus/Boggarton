package com.foxcatgames.boggarton.players;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.game.AbstractGame;

public class RealPlayer {

    private final AbstractGame game;

    public RealPlayer(final AbstractGame game, final int keyLeft, final int keyRight,
            final int keyDown, final int keyRotate) {
        this.game = game;
        addKeyListeners(keyLeft, keyRight, keyDown, keyRotate);
    }

    private void addKeyListeners(final int keyLeft, final int keyRight,
            final int keyDown, final int keyRotate) {
        addMoveLeft(keyLeft);
        addMoveRight(keyRight);
        addMoveDown(keyDown);
        addRotate(keyRotate);
    }

    private void addMoveLeft(final int keyLeft) {
        final KeyListener moveLeft = new KeyListener() {
            @Override
            public void onKeyDown() {
                game.moveLeft();
            }
        };
        EventManager.getInstance().addListener(keyLeft, moveLeft);
    }

    private void addMoveRight(final int keyRight) {
        final KeyListener moveRight = new KeyListener() {
            @Override
            public void onKeyDown() {
                game.moveRight();
            }
        };
        EventManager.getInstance().addListener(keyRight, moveRight);
    }

    private void addMoveDown(final int keyDown) {
        final KeyListener moveDown = new KeyListener() {
            @Override
            public void onKeyDown() {
                game.setMaxSpeed();
                game.dropFigure();
            }

            @Override
            public void onKeyUp() {
                game.restoreSpeed();
            }

        };
        EventManager.getInstance().addListener(keyDown, moveDown);
    }

    private void addRotate(final int keyRotate) {
        final KeyListener rotate = new KeyListener() {
            @Override
            public void onKeyUp() {
                game.rotateFigure();
            }
        };
        EventManager.getInstance().addListener(keyRotate, rotate);
    }
}
