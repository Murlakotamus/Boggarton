package com.foxcatgames.boggarton.game.glass;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.utils.Utils;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class MultiplayerGlass extends SimpleGlass {

    private final Layer layer;
    private final int difficulty;
    private int count = 0;

    public MultiplayerGlass(final Layer layer, final Vector2f position, final int width, final int height, final int difficulty, final int... sounds) {
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

        int brick = 1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < state.getWidth(); i++) {
            switch (yuckType) {
            case PROBABILISTIC:
                brick = Utils.getBrick(difficulty, RandomTypes.PROBABILISTIC.getRandomType());
                break;
            case HARD:
                brick = getHardBrick();
                break;
            case RANDOM:
                brick = Utils.getBrick(difficulty, RandomTypes.RANDOM.getRandomType());
            case NONE:
            default:
                throw new IllegalStateException("Incredible situation!");
            }
            result.append(brick - Const.CURRENT_SET * 10);
            state.setBrick(i, state.getHeight() - 1, new Brick(brick, layer));
        }

        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++)
                if (state.getBrick(i, j) != null)
                    addBrick(i, j);

        if (yuckType == YuckTypes.HARD) {
            int delta = difficulty - 4;
            count += delta;
        }
        return result.toString();
    }

    private int getHardBrick() {
        int result = count++;
        if (result >= difficulty)
            result = result % difficulty;

        return result + Const.CURRENT_SET * 10 + 1;
    }
}
