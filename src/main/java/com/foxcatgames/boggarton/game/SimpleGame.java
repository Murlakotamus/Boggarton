package com.foxcatgames.boggarton.game;

import java.util.Map;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class SimpleGame extends AbstractOnePlayerGame {

    public SimpleGame(final Layer layer, final int x, final int y, final int width, final int height, final int forecast, final int lenght,
            final int difficulty, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, width, height, forecast, lenght, difficulty, randomType, sounds);
    }
}
