package com.foxcatgames.boggarton.players;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.MultiplayerGame;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;

public class RealPlayer implements IPlayer {

    private final AbstractGame game;

    public RealPlayer(final AbstractGame game, final int keyLeft, final int keyRight, final int keyDown, final int keyRotate) {
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
    public GameOutcomeParams getSurrogatePlayerParams() {
        final GameOutcomeParams.Builder builder = new GameOutcomeParams.Builder();

        builder.setPrognosisDebth(game.getForecast().getDepth());
        builder.setFigureSize(game.getForecast().getFigureSize());
        builder.setScore(game.getGlass().getGlassState().getScore());

        if (game.getForecast() instanceof SimpleForecast)
            builder.setSetSize(((SimpleForecast) game.getForecast()).getDifficulty());

        if (game.getForecast() instanceof SimpleForecast)
            builder.setRandomName(((SimpleForecast) game.getForecast()).getRandomType().getName());

        if (game.getGlass() instanceof SimpleGlass)
            builder.setCount(((SimpleGlass) game.getGlass()).getCount());

        builder.setPlayerName(getName());

        if (game instanceof MultiplayerGame)
            builder.setYuckName(((MultiplayerGame) game).getYuckType().getName());

        builder.setVirtual(false);

        return builder.build();
    }

    public String getName() {
        return game.getName();
    }

}
