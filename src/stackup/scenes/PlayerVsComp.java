package stackup.scenes;

import org.lwjgl.input.Keyboard;

import stackup.players.RealPlayer;
import stackup.players.virtual.EffectiveVirtualAdaptivePlayer;

public class PlayerVsComp extends AbstractMultiPlayer {

    public PlayerVsComp(final int width, final int height, final int[] forecast, final int lenght) {
        super(Scene.PLAYER_VS_COMP, width, height, forecast, lenght, 2);

        new EffectiveVirtualAdaptivePlayer(game[0]);
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
