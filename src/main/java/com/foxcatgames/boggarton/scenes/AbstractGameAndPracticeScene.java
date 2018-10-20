package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_PAUSED;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.AbstractOnePlayerGame;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;

abstract public class AbstractGameAndPracticeScene<T extends AbstractOnePlayerGame> extends AbstractOnePlayerScene<Brick, SimpleFigure, SimpleGlass, SimpleForecast, T> {

    private final SimpleEntity gamePaused = new SimpleEntity(GAME_PAUSED, layer);

    public AbstractGameAndPracticeScene(final SceneItem scene) {
        super(scene);
    }

    @Override
    protected void hideGlass() {
        if (game.isGameOver())
            return;
        game.getGlass().pauseOn();
        gamePaused.spawn(new Vector2f(X + getFigureSize(game) * BOX + 25, Y + BOX * 3 + BORDER)); // @FIXME 25 - ???
    }

    @Override
    protected void showGlass() {
        if (game.isGameOver())
            return;
        gamePaused.unspawn();
        game.getGlass().pauseOff();
    }
}
