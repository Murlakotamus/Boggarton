package stackup.scenes;

import stackup.game.SimpleGame;
import stackup.players.virtual.VirtualAdaptivePlayer;

public class OnePlayerDemo extends AbstractOnePlayerGame {

    public OnePlayerDemo(final int width, final int height, final int forecast, final int lenght) {
        super(Scene.ONE_PLAYER_DEMO);
        game = new SimpleGame(layer, X, Y, width, height, Math.min(prognosis, forecast),
                lenght, difficulty);
        game.startGame();
        new VirtualAdaptivePlayer(game);
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }

}
