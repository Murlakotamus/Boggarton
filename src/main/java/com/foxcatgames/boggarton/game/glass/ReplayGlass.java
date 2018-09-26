package com.foxcatgames.boggarton.game.glass;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.utils.Utils;

public class ReplayGlass extends AbstractVisualGlass {

    private final Layer layer;

    public ReplayGlass(final Layer layer, final Vector2f position, final int width, final int height, final Map<String, Integer> sounds) {
        super(layer, position, width, height, sounds);
        this.layer = layer;
    }

    public ReplayGlass(final Layer layer, final Vector2f position, final int width, final int height, final int[][] bricks, final Map<String, Integer> sounds) {
        super(layer, position, width, height, bricks, sounds);
        this.layer = layer;
    }

    public void executeYuck(final String yuckBricks) {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++) {
                if (j > 0)
                    state.setBrick(i, j - 1, state.getBrick(i, j));
                removeBrick(i, j);
            }

        for (int i = 0; i < state.getWidth(); i++)
            state.setBrick(i, state.getHeight() - 1, new Brick(Utils.parseBrick(yuckBricks, i), layer));
    }
}
