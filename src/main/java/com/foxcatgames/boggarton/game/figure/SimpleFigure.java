package com.foxcatgames.boggarton.game.figure;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.utils.Utils;

public class SimpleFigure extends AbstractVisualFigure {

    public SimpleFigure(final Layer layer, final Vector2f position, final int size, final int difficulty) {
        super(size, position);

        number = size;
        for (int j = 0; j < size; j++)
            bricks[j] = new Brick(Utils.randomBrick(difficulty), layer);

        respawn();
    }
}
