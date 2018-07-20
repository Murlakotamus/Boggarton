package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.players.RealPlayer;
import com.foxcatgames.boggarton.players.virtual.EffectiveVirtualAdaptivePlayer;
import com.foxcatgames.boggarton.players.virtual.solver.Price;

public class PlayerVsCompGame extends AbstractMultiplayerGame {

    public PlayerVsCompGame(final int width, final int height, final int[] forecast,
            final int lenght, final Yucks yuckType) {
        super(SceneItem.PLAYER_VS_COMP, width, height, forecast, lenght, 2, yuckType);

        new EffectiveVirtualAdaptivePlayer(game[0], new Price());
        new RealPlayer(game[1], Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN,
                Keyboard.KEY_UP);
    }

    @Override
    protected void checkAuto() {
    }

    @Override
    protected void changes() {
        super.changes();
    }
}
