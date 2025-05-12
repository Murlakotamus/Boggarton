package com.foxcatgames.boggarton.scenes.gamescenes;

import com.foxcatgames.boggarton.scenes.types.GameTypes;
import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.RealGame;
import com.foxcatgames.boggarton.players.real.RealSingleplayer;
import com.foxcatgames.boggarton.scenes.AbstractGameAndPracticeScene;
import com.foxcatgames.boggarton.scenes.SceneItem;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class GameScene extends AbstractGameAndPracticeScene<RealGame> {

    public GameScene(final int width, final int height, final int forecast, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty, final GameTypes gameType) {

        super(SceneItem.GAME);

        game = new RealGame(layer, X, Y, width, height, forecast, figureSize, difficulty.getSetSize(), randomType, Const.SOUNDS);
        game.setName("Human game");
    }

    @Override
    protected void start() {
        game.startGame();
        player = new RealSingleplayer(game, Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_UP);
    }

    @Override
    protected void terminate() {
        super.terminate();
    }
}
