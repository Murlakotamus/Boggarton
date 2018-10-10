package com.foxcatgames.boggarton.game.figure;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.utils.Utils;

public class PredefinedFigure extends AbstractVisualFigure<Brick> {

    public PredefinedFigure(final Layer layer, final Vector2f position, final int size, String figure) {
        super(new Brick[size], position);

        for (int i = 0; i < size; i++)
            bricks[i] = new Brick(Utils.parseBrick(figure, i), layer);

        respawn();
    }
}
