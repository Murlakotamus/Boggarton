package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_OVER;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.game.utils.DbHandler;
import com.foxcatgames.boggarton.players.IPlayer;

abstract public class AbstractOnePlayerScene<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>>
        extends AbstractPlayingScene<B, F, G, P> {

    protected static final int X = 355;
    protected final SimpleEntity gameOver = new SimpleEntity(GAME_OVER, layer);;
    protected AbstractVisualGame<B, F, G, P> game;

    protected IPlayer player;

    public AbstractOnePlayerScene(final SceneItem scene) {
        super(scene);
    }

    protected void saveOutcome(final IPlayer player) {
        DbHandler.saveOutcome(player);
    }

    @Override
    protected void changes() {
        if (game.isGameOver() && gameOver == null) {
            gameOver.spawn(new Vector2f(X + getFigureSize(game) * BOX + 25, Y + BOX * 3 + BORDER));
            saveOutcome(player);
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
