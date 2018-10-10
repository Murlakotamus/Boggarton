package com.foxcatgames.boggarton.game.forecast;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class SimpleForecast extends AbstractVisualForecast<Brick, SimpleFigure> {

    public SimpleForecast(final Layer layer, final Vector2f startPos, final int prognosis, final int figureSize, final int setSize,
            final RandomTypes randomType) {

        super(new SimpleFigure[prognosis], setSize, randomType);

        frame = new Frame(layer, startPos, figureSize, prognosis, false, true);

        for (int i = 0; i < prognosis; i++)
            figures[i] = new SimpleFigure(layer, new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + i * BOX + BORDER), figureSize, setSize,
                    randomType.getRandomType(), null);
    }

    @Override
    public void setNext(final List<Pair<Integer, Integer>> pairs) {
        for (int i = 0; i < figures.length - 1; i++) {
            figures[i] = figures[i + 1];
            figures[i].shiftY(-BOX);
        }

        figures[figures.length - 1] = new SimpleFigure(frame.getLayer(),
                new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + (figures.length - 1) * BOX + BORDER), getFigureSize(), difficulty,
                randomType.getRandomType(), pairs);
    }
}
