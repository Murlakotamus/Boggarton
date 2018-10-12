package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

abstract public class AbstractOnePlayerGame extends AbstractVisualGame<Brick, SimpleFigure, SimpleGlass, SimpleForecast> {

    public AbstractOnePlayerGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, width, height, figureSize, setSize, randomType, sounds);
        glass = new SimpleGlass(layer, new Vector2f(x + figureSize * BOX + 20, y), width, height, sounds);
        forecast = new SimpleForecast(layer, new Vector2f(x, y), prognosis, figureSize, setSize, randomType);

    }

    @Override
    protected void nextStage() {
        startTime = getTime();
        previousTime = startTime;
        stage = stage.getNextStage(reactionDetected);
    }
}
