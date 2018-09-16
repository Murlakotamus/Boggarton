package com.foxcatgames.boggarton.game.glass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.utils.Utils;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class MultiplayerGlass extends AbstractVisualGlass {

    private final Layer layer;
    private final int difficulty;
    private int count = 0;

    public MultiplayerGlass(final Layer layer, final Vector2f position, final int width, final int height, final int difficulty, final Map<String, Integer> sounds) {
        super(layer, position, width, height, sounds);
        this.layer = layer;
        this.difficulty = difficulty;
    }

    public String executeYuck(YuckTypes yuckType) {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++) {
                if (j > 0)
                    state.setBrick(i, j - 1, state.getBrick(i, j));
                removeBrick(i, j);
            }

        boolean yuckAdded = false;
        final ArrayList<Integer> yuckBricks = new ArrayList<>(state.getWidth());
        for (int i = 0; i < state.getWidth(); i++) {
            int brick;
            switch (yuckType) {
            case INCONSOLABLE:
                brick = Utils.random(difficulty + 1);
                if (!yuckAdded && brick == difficulty) {
                    brick = Const.EMPTY;
                    yuckAdded = true;
                } else
                    brick = Utils.getBrick(difficulty, RandomTypes.RANDOM.getRandomType());
                break;
            case HARD:
                brick = getHardBrick();
                break;
            case RANDOM:
                brick = Utils.getBrick(difficulty, RandomTypes.RANDOM.getRandomType());
                break;
            case NONE:
            default:
                throw new IllegalStateException("Incredible situation!");
            }
            yuckBricks.add(brick);
        }

        if (yuckType == YuckTypes.HARD)
            count += difficulty - 4;

        // to eliminate bias
        if (yuckType == YuckTypes.INCONSOLABLE)
            Collections.shuffle(yuckBricks);

        for (int i = 0; i < state.getWidth(); i++)
            state.setBrick(i, state.getHeight() - 1, new Brick(yuckBricks.get(i), layer));

        final StringBuilder result = new StringBuilder(state.getWidth());
        for (int i = 0; i < state.getWidth(); i++)
            result.append(yuckBricks.get(i) - Const.CURRENT_SET * 10);

        return result.toString();
    }

    private int getHardBrick() {
        int result = count++;
        if (result >= difficulty)
            result = result % difficulty;

        return result + Const.CURRENT_SET * 10 + 1;
    }
}
