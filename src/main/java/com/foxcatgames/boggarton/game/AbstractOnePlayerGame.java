package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

abstract public class AbstractOnePlayerGame extends AbstractGame {
    public AbstractOnePlayerGame(final Layer layer, final int x, final int y, final int width, final int height, final int forecast, final int lenght,
            final int difficulty, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, width, height, forecast, lenght, difficulty, randomType, sounds);
        glass = new SimpleGlass(layer, new Vector2f(x + lenght * BOX + 20, y), width, height, sounds);
    }

    @Override
    protected void nextStage() {
        startTime = getTime();
        previousTime = startTime;
        stage = stage.getNextStage(reactionDetected);
    }
}
