package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_OVER;

import java.sql.SQLException;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.utils.DbHandler;
import com.foxcatgames.boggarton.players.IPlayer;

abstract public class AbstractOnePlayerScene extends AbstractPlayingScene {

    protected static final int X = 355;
    protected SimpleEntity gameOver;
    protected AbstractGame game;

    protected IPlayer player;

    public AbstractOnePlayerScene(final SceneItem scene) {
        super(scene);
    }

    protected void saveOutcome() {
        try {
            DbHandler.getInstance().saveGameOutcome(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void changes() {
        if (game.isGameOver() && gameOver == null) {
            gameOver = new SimpleEntity(GAME_OVER, layer);
            gameOver.spawn(new Vector2f(X + figureSize * BOX + 25, Y + BOX * 3 + BORDER));
            saveOutcome();
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
