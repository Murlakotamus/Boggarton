package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.players.RealPlayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class TwoPlayersGame extends AbstractMultiplayerGame {

    public TwoPlayersGame(final int width, final int height, final int[] forecast, final int figureSize, final YuckTypes yuckType, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        super(SceneItem.PLAYER_VS_COMP, width, height, forecast, figureSize, 2, yuckType, randomType, difficulty);

        first = new RealPlayer(game[0], Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_S, Keyboard.KEY_W);
        second = new RealPlayer(game[1], Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_UP);
    }

    @Override
    protected void checkAuto() {
    }

    @Override
    protected void changes() {
        super.changes();
    }

    @Override
    protected void start() {
    }
}
