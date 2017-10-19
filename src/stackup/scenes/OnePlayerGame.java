package stackup.scenes;

import static stackup.Const.BORDER;
import static stackup.Const.BOX;
import static stackup.Const.GAME_PAUSED;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import stackup.entity.SimpleEntity;
import stackup.game.SimpleGame;
import stackup.game.SimpleGlass;
import stackup.players.RealPlayer;

public class OnePlayerGame extends AbstractOnePlayerGame {

    private final SimpleEntity gamePaused;

    public OnePlayerGame(final int width, final int height, final int forecast, final int lenght) {
        super(Scene.ONE_PLAYER_GAME);
        gamePaused = new SimpleEntity(GAME_PAUSED, layer);

        game = new SimpleGame(layer, X, Y, width, height, Math.min(prognosis, forecast),
                lenght, difficulty);
        new RealPlayer(game, Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN,
                Keyboard.KEY_UP);
        game.startGame();
    }

    @Override
    protected void hideGlass() {
        if (game.isGameOver())
            return;
        ((SimpleGlass) game.getGlass()).pauseOn();
        gamePaused.spawn(new Vector2f(X + size * 30 + 25, Y + BOX * 3 + BORDER));
    }

    @Override
    protected void showGlass() {
        if (game.isGameOver())
            return;
        gamePaused.unspawn();
        ((SimpleGlass) game.getGlass()).pauseOff();
    }
}
