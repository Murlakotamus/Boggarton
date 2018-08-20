package com.foxcatgames.boggarton.game.forecast;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class SimpleForecast extends AbstractVisualForecast {

    final private int difficulty;
    final private RandomTypes randomType;

    public SimpleForecast(final Layer layer, final Vector2f startPos, final int prognosis, final int size, final int difficulty, final RandomTypes randomType) {

        super(prognosis, size);
        this.difficulty = difficulty;
        this.randomType = randomType;
        frame = new Frame(layer, startPos, size, prognosis, false, true);

        for (int i = 0; i < prognosis; i++)
            figures[i] = new SimpleFigure(layer, new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + i * BOX + BORDER), size, difficulty,
                    randomType.getRandomType(), null);
    }

    @Override
    public void setNext(final List<Pair<Integer, Integer>> pairs) {
        for (int i = 0; i < prognosis - 1; i++) {
            figures[i] = figures[i + 1];
            ((SimpleFigure) figures[i]).shiftY(-BOX);
        }

        figures[prognosis - 1] = new SimpleFigure(frame.getLayer(),
                new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + (prognosis - 1) * BOX + BORDER), size, difficulty,
                randomType.getRandomType(), pairs);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public RandomTypes getRandomType() {
        return randomType;
    }

}
