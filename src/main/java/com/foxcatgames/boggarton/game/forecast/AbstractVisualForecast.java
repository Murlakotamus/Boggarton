package com.foxcatgames.boggarton.game.forecast;

import java.util.List;

import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

abstract public class AbstractVisualForecast extends AbstractForecast {

    protected Frame frame;

    public AbstractVisualForecast(final int prognosis, final int size) {
        this(prognosis, size, 0, null);
    }

    public AbstractVisualForecast(final int prognosis, final int size, final int difficulties, final RandomTypes randomType) {
        super(prognosis, size, difficulties, randomType);
    }

    abstract public void setNext(final List<Pair<Integer, Integer>> pairs);

    @Override
    protected IFigure[] initFigures() {
        return new IFigure[prognosis];
    }

    public void unspawn() {
        frame.unspawn();
        for (int i = 0; i < prognosis; i++)
            ((AbstractVisualFigure) figures[i]).unspawn();
    }
}
