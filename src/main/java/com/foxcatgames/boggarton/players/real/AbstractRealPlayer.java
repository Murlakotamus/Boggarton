package com.foxcatgames.boggarton.players.real;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.players.IPlayer;

abstract public class AbstractRealPlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>>
        implements IPlayer {

    protected final AbstractVisualGame<B, F, G, P> game;

    public AbstractRealPlayer(final AbstractVisualGame<B, F, G, P> game, final int keyLeft, final int keyRight,
            final int keyDown, final int keyRotate) {
        this.game = game;
        addKeyListeners(keyLeft, keyRight, keyDown, keyRotate);
    }

    private void addKeyListeners(final int keyLeft, final int keyRight, final int keyDown, final int keyRotate) {
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

    @Override
    public String getName() {
        return game.getName();
    }

    @Override
    public GameParams getGameParams() {
        final GameParams.Builder builder = game.buildParams();

        builder.setPlayerName(getName());
        builder.setVirtual(false);

        return builder.build();
    }
}
