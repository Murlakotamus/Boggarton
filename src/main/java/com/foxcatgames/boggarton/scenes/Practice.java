package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.SimpleGame;
import com.foxcatgames.boggarton.players.RealPlayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class Practice extends AbstractGameAndPractice {

    public Practice(final int width, final int height, final int forecast, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        super(SceneItem.PRACTICE);

        game = new SimpleGame(layer, X, Y, width, height, Math.min(prognosis, forecast), figureSize, difficulty.getSetSize(), randomType, Const.SOUNDS);
        game.setName("Human practice");
        game.startGame();
        player = new RealPlayer(game, Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_UP);
    }

    @Override
    protected void start() {
    }
}