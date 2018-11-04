package com.foxcatgames.boggarton.game.glass;

import java.util.ArrayList;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.game.utils.Utils;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class MultiplayerGlass extends AbstractVisualGlass<Brick, SimpleFigure> {

    private final Layer layer;
    private final int difficulty;
    private int count;

    public MultiplayerGlass(final Layer layer, final Vector2f position, final int width, final int height, final int difficulty,
            final Map<String, Integer> sounds) {
        super(new Brick[width][height], layer, position, width, height, sounds, 0);
        this.layer = layer;
        this.difficulty = difficulty;
    }

    public String executeYuck(final YuckTypes yuckType) {
        if (yuckType == YuckTypes.NASTY)
            return nastyBrick();

        final ArrayList<Integer> yuckBricks = new ArrayList<>(state.getWidth());
        for (int i = 0; i < width(); i++) {
            final int brick;
            switch (yuckType) {
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

        raiseBricks();
        for (int i = 0; i < width(); i++)
            state.setBrick(i, height() - 1, new Brick(yuckBricks.get(i), layer));

        final StringBuilder result = new StringBuilder(state.getWidth());
        for (int i = 0; i < width(); i++)
            result.append((char) (yuckBricks.get(i) - Const.CURRENT_SET * 10 + 64));

        return result.toString();
    }

    private String nastyBrick() {
        final ArrayList<Pair<Integer, Integer>> places = new ArrayList<>();
        for (int i = 0; i < width(); i++) {
            final int fullness = state.getEmptyHeight(i);
            if (fullness >= 0)
                places.add(new Pair<>(i, fullness));
        }

        final int size = places.size();
        if (size > 0) {
            final Pair<Integer, Integer> place = places.get(Utils.random(size));
            final int brick = Utils.random(difficulty + 1) == difficulty ? Const.EMPTY : Utils.getBrick(difficulty, RandomTypes.RANDOM.getRandomType());
            state.setBrick(place.getFirst(), place.getSecond(), new Brick(brick, layer));
            return place.getFirst() + ", " + place.getSecond() + ", " + (char) (brick - Const.CURRENT_SET * 10 + 64);
        }
        return null;
    }

    private int getHardBrick() {
        int result = count++;
        if (result >= difficulty)
            result = result % difficulty;

        return result + Const.CURRENT_SET * 10 + 1;
    }
}
