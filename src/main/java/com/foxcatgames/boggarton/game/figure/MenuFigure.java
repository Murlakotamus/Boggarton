package com.foxcatgames.boggarton.game.figure;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;

public class MenuFigure extends AbstractVisualFigure {

    public MenuFigure(final Layer layer, final Vector2f position, final int size, final int difficulty) {
        super(size, position);

        for (int j = difficulty, i = 0; j > difficulty - size; j--, i++) {
            int value = difficulty - i % difficulty;
            value = value + Const.CURRENT_SET * 10;
            bricks[i] = new Brick(value, layer);
        }
        respawn();
    }
}
