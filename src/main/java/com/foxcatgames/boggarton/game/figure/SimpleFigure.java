package com.foxcatgames.boggarton.game.figure;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.game.utils.Utils;

public class SimpleFigure extends AbstractVisualFigure {

    public SimpleFigure(final Layer layer, final Vector2f position, final int size, final int difficulty, final int[] randomType,
            final List<Pair<Integer, Integer>> pairs) {
        super(size, position);

        number = size;
        for (int j = 0; j < size; j++) {
            int value = Utils.getBrick(difficulty, randomType);

            if (pairs != null)
                for (Pair<Integer, Integer> pair : pairs)
                    if (j == pair.getFirst())
                        value = pair.getSecond();

            bricks[j] = new Brick(value, layer);
        }
        respawn();
    }

}
