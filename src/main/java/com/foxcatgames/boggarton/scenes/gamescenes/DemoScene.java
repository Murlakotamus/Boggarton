package com.foxcatgames.boggarton.scenes.gamescenes;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AutomatedSimpleGame;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;
import com.foxcatgames.boggarton.scenes.AbstractOnePlayerScene;
import com.foxcatgames.boggarton.scenes.SceneItem;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class DemoScene extends AbstractOnePlayerScene<Brick, SimpleFigure, SimpleGlass, SimpleForecast, AutomatedSimpleGame> {

    private final int prognosis;

    public DemoScene(final int width, final int height, final int prognosis, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        super(SceneItem.DEMO);
        this.prognosis = prognosis;
        game = new AutomatedSimpleGame(layer, X, Y, width, height, prognosis, figureSize, difficulty.getSetSize(), randomType, Const.SOUNDS);
        game.setName("Virtual");
    }

    @Override
    protected void start() {
        game.initLogger();
        game.startGame();
        player = new VirtualAdaptivePlayer<>(game, prognosis, Const.FULLNESS_EATER);
    }

    @Override
    protected void terminate() {
        game.closeLogger();
        super.terminate();
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }
}
