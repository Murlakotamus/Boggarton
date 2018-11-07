package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class SimpleGame extends AbstractSimpleGame {

    public SimpleGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, prognosis, figureSize, setSize, randomType, sounds);
        glass = new SimpleGlass(layer, new Vector2f(x + figureSize * BOX + 20, y), width, height, sounds);
    }

    @Override
    protected void changeHappens() {
    }
}
