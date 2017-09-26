package stackup.scenes;

import static stackup.Const.GAME_OVER;
import static stackup.Const.LENGHT;
import static stackup.Const.BOX;

import org.lwjgl.util.vector.Vector2f;

import stackup.entity.Frame;
import stackup.entity.SimpleEntity;
import stackup.game.SinglePlayerGame;
import stackup.players.virtual.VirtualAdaptivePlayer;

public class OnePlayerDemo extends AbstractGameScene {

    private static final int X = 200;
    private static final int Y = 100;

    private final SinglePlayerGame game;

    private SimpleEntity gameOver = null;

    public OnePlayerDemo(final int width, final int height, final int forecast, final int lenght) {
        super(Scene.ONE_PLAYER_DEMO);

        assert (width < lenght) : "Glass width less than figure width";

        game = new SinglePlayerGame(layer, X, Y, width, height, forecast, lenght, setSize);
        game.startGame();
        new VirtualAdaptivePlayer(game);
    }

    @Override
    protected void changes() {
        if (game.isGameOver() && gameOver == null) {
            gameOver = new SimpleEntity(GAME_OVER, layer);
            gameOver.spawn(new Vector2f(X + 115, Y + BOX * LENGHT + Frame.BORDER));
        } else if (game.isGameOn())
            game.processStage();
    }

    @Override
    protected void setGameOver() {
        super.setGameOver();
        game.setGameOver();
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }

}
