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
        if (yuckBricks.length() == 0)
            return;

        if (yuckBricks.contains(",")) { // nasty brick
            String[] yuck = yuckBricks.split(", ");
            state.setBrick(Integer.parseInt(yuck[0]), Integer.parseInt(yuck[1]), new Brick(Integer.parseInt(yuck[2]), layer));
            return;
        }
        raiseBricks();
        for (int i = 0; i < state.getWidth(); i++)
            state.setBrick(i, state.getHeight() - 1, new Brick(Utils.parseBrick(yuckBricks, i), layer));
    }
}
