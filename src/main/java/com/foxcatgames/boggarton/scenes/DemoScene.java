package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.SimpleGame;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class DemoScene extends AbstractOnePlayerScene {

    public DemoScene(final int width, final int height, final int prognosis, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        super(SceneItem.DEMO);
        game = new SimpleGame(layer, X, Y, width, height, prognosis, figureSize, difficulty.getSetSize(), randomType, Const.SOUNDS);
        game.setName("Virtual");
        game.initLogger();
        game.startGame();
        player = new VirtualAdaptivePlayer(game, Const.PRICE);
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
