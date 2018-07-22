package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.game.SimpleGame;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;
import com.foxcatgames.boggarton.players.virtual.solver.Price;

public class OnePlayerDemo extends AbstractOnePlayerGame {

    public OnePlayerDemo(final int width, final int height, final int forecast, final int figureSize, final int[] randomType) {
        super(SceneItem.ONE_PLAYER_DEMO);
        game = new SimpleGame(layer, X, Y, width, height, Math.min(prognosis, forecast), figureSize, difficulty, randomType);
        game.setName("Virtual");
        game.initLogger();
        game.startGame();
        new VirtualAdaptivePlayer(game, new Price());
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }
}
