package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.SimpleGame;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;
import com.foxcatgames.boggarton.players.virtual.solver.Price;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class Demo extends AbstractOnePlayerScene {

    public Demo(final int width, final int height, final int forecast, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        super(SceneItem.DEMO);
        game = new SimpleGame(layer, X, Y, width, height, Math.min(prognosis, forecast), figureSize, difficulty.getSetSize(), randomType, Const.SOUNDS);
        game.setName("Virtual");
        game.initLogger();
        game.startGame();
        player = new VirtualAdaptivePlayer(game, new Price());
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }

    @Override
    protected void start() {
    }
}