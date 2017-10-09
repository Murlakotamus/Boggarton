package stackup.scenes;

import org.lwjgl.input.Keyboard;

import stackup.players.RealPlayer;

public class PlayerVsPlayer extends AbstractMultiPlayer {

    public PlayerVsPlayer(final int width, final int height, final int[] forecast, final int lenght) {
        super(Scene.PLAYER_VS_COMP, width, height, forecast, lenght, 2);

        new RealPlayer(game[0], Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_S, Keyboard.KEY_W);

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