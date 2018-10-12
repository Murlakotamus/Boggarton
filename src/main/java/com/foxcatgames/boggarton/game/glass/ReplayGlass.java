package com.foxcatgames.boggarton.game.glass;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.figure.PredefinedFigure;
import com.foxcatgames.boggarton.game.utils.Utils;

public class ReplayGlass extends AbstractVisualGlass<Brick, PredefinedFigure> {

    private final Layer layer;

    public ReplayGlass(final Layer layer, final Vector2f position, final int width, final int height, final int[][] bricks, final Map<String, Integer> sounds) {
        super(new Brick[width][height], layer, position, width, height, sounds);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (bricks[i][j] > 0)
                    state.setBrick(i, j, new Brick(bricks[i][j] + Const.CURRENT_SET * 10, layer));

        respawn();
        this.layer = layer;
    }

    public void executeYuck(final String yuckBricks) {
        if (yuckBricks.length() == 0)
            return;

        if (yuckBricks.contains(",")) { // nasty brick
            final String[] yuck = yuckBricks.split(", ");
            state.setBrick(Integer.parseInt(yuck[0]), Integer.parseInt(yuck[1]), new Brick(Integer.parseInt(yuck[2]), layer));
            return;
        }
        raiseBricks();
        for (int i = 0; i < width(); i++)
            state.setBrick(i, height() - 1, new Brick(Utils.parseBrick(yuckBricks, i), layer));
    }
}
