package com.foxcatgames.boggarton.game;

import java.util.Map;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class SimpleGame extends AbstractOnePlayerGame {

    public SimpleGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, width, height, prognosis, figureSize, setSize, randomType, sounds);
    }
}
