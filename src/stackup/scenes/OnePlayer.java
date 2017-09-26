package stackup.scenes;

import static stackup.Const.GAME_OVER;
import static stackup.Const.GAME_PAUSED;
import static stackup.Const.LENGHT;
import static stackup.Const.BOX;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import stackup.entity.Frame;
import stackup.entity.SimpleEntity;
import stackup.game.Glass;
import stackup.game.SinglePlayerGame;
import stackup.players.RealPlayer;

public class OnePlayer extends AbstractGameScene {

    private static final int X = 200;
    private static final int Y = 100;
    private final SinglePlayerGame game;
    
    private SimpleEntity gameOver;
    private SimpleEntity gamePaused;

    public OnePlayer(final int width, final int height, final int forecast, final int lenght) {
        super(Scene.ONE_PLAYER_GAME);
        gamePaused = new SimpleEntity(GAME_PAUSED, layer);

        game = new SinglePlayerGame(layer, X, Y, width, height, forecast, lenght, setSize);
        new RealPlayer(game, Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_UP);
        game.startGame();
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
        if (game.isGameOver())
            return;
        ((Glass)game.getGlass()).pauseOn();
        gamePaused.spawn(new Vector2f(X + 115, Y + BOX * LENGHT + Frame.BORDER));
    }

    @Override
    protected void showGlass() {
        if (game.isGameOver())
            return;
        gamePaused.unspawn();
        ((Glass)game.getGlass()).pauseOff();
    }
}
