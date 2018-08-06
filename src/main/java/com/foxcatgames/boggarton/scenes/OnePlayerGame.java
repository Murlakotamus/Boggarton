package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_PAUSED;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.SimpleGame;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.players.RealPlayer;

public class OnePlayerGame extends AbstractOnePlayerGame {

    private final SimpleEntity gamePaused;

    public OnePlayerGame(final int width, final int height, final int forecast, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        super(SceneItem.PRACTICE);
        gamePaused = new SimpleEntity(GAME_PAUSED, layer);

        game = new SimpleGame(layer, X, Y, width, height, Math.min(prognosis, forecast), figureSize, difficulty.getSetSize(), randomType);
        game.setName("Human");
        game.startGame();
        player = new RealPlayer(game, Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_UP);
    }

    @Override
    protected void hideGlass() {
        if (game.isGameOver())
            return;
        ((SimpleGlass) game.getGlass()).pauseOn();
        gamePaused.spawn(new Vector2f(X + figureSize * 30 + 25, Y + BOX * 3 + BORDER));
    }

    @Override
    protected void showGlass() {
        if (game.isGameOver())
            return;
        gamePaused.unspawn();
        ((SimpleGlass) game.getGlass()).pauseOff();
    }

    @Override
    protected void start() {
    }
}
