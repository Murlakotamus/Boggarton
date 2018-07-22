package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_OVER;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.AbstractGame;

abstract public class AbstractOnePlayerGame extends AbstractGameScene {

    protected static final int X = 355;
    protected SimpleEntity gameOver;
    protected AbstractGame game;

    public AbstractOnePlayerGame(final SceneItem scene) {
        super(scene);
    }

    @Override
    protected void changes() {
        if (game.isGameOver() && gameOver == null) {
            gameOver = new SimpleEntity(GAME_OVER, layer);
            gameOver.spawn(new Vector2f(X + figureSize * BOX + 25, Y + BOX * 3 + BORDER));
        } else if (game.isGameOn())
            game.processStage();
    }

    @Override
    protected void setGameOver() {
        super.setGameOver();
        game.setGameOver();
        game.closeLogger();
    }
}
