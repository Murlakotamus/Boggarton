package stackup.scenes;

import static stackup.Const.BORDER;
import static stackup.Const.BOX;
import static stackup.Const.GAME_OVER;

import org.lwjgl.util.vector.Vector2f;

import stackup.entity.SimpleEntity;
import stackup.game.SinglePlayerGame;

abstract public class AbstractSinglePlayerGame extends AbstractGameScene {

    protected static final int X = 350;
    protected SimpleEntity gameOver;;
    protected SinglePlayerGame game;

    public AbstractSinglePlayerGame(final Scene scene) {
        super(scene);
    }

    @Override
    protected void changes() {
        if (game.isGameOver() && gameOver == null) {
            gameOver = new SimpleEntity(GAME_OVER, layer);
            gameOver.spawn(new Vector2f(X + size * 30 + 25, Y + BOX * 3 + BORDER));
        } else if (game.isGameOn())
            game.processStage();
    }

    @Override
    protected void setGameOver() {
        super.setGameOver();
        game.setGameOver();
    }
}
