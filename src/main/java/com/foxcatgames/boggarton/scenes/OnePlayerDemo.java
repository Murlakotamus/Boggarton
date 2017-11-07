package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.game.SimpleGame;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;

public class OnePlayerDemo extends AbstractOnePlayerGame {

    public OnePlayerDemo(final int width, final int height, final int forecast, final int lenght) {
        super(SceneItem.ONE_PLAYER_DEMO);
        game = new SimpleGame(layer, X, Y, width, height, Math.min(prognosis, forecast),
                lenght, difficulty);
        game.setName("Virtual");
        game.initLogger();
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
