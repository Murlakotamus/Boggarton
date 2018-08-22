package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_PAUSED;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;

abstract public class AbstractGameAndPractice extends AbstractOnePlayerScene {

    private final SimpleEntity gamePaused = new SimpleEntity(GAME_PAUSED, layer);

    public AbstractGameAndPractice(final SceneItem scene) {
        super(scene);
    }

    @Override
    protected void hideGlass() {
        if (game.isGameOver())
            return;
        ((AbstractVisualGlass) game.getGlass()).pauseOn();
        gamePaused.spawn(new Vector2f(X + figureSize * 30 + 25, Y + BOX * 3 + BORDER));
    }

    @Override
    protected void showGlass() {
        if (game.isGameOver())
            return;
        gamePaused.unspawn();
        ((AbstractVisualGlass) game.getGlass()).pauseOff();
    }
}
