package com.foxcatgames.boggarton.game;

import java.util.Map;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

abstract public class AbstractSimpleGame extends AbstractOnePlayerGame {

    public AbstractSimpleGame(final Layer layer, final int x, final int y, final int prognosis, final int figureSize,
            final int setSize, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, prognosis, figureSize, setSize, randomType, sounds);
    }
}
