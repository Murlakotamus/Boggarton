package com.foxcatgames.boggarton.scenes.gamescenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.AbstractOnePlayerGame;
import com.foxcatgames.boggarton.game.SimpleGame;
import com.foxcatgames.boggarton.players.real.RealSingleplayer;
import com.foxcatgames.boggarton.scenes.AbstractGameAndPracticeScene;
import com.foxcatgames.boggarton.scenes.SceneItem;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class PracticeScene extends AbstractGameAndPracticeScene<AbstractOnePlayerGame> {

    public PracticeScene(final int width, final int height, final int prognosis, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        super(SceneItem.PRACTICE);
        game = new SimpleGame(layer, X, Y, width, height, prognosis, figureSize, difficulty.getSetSize(), randomType, Const.SOUNDS);
        game.setName("Human practice");
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
