package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;

abstract public class AbstractVisualForecast extends AbstractForecast {

    protected Frame frame;

    public AbstractVisualForecast(final int prognosis, final int size) {
        super(prognosis, size);
    }

    abstract public void setNext();

    @Override
    protected IFigure[] initFigures() {
        return new IFigure[prognosis];
    }

    public void unspawn() {
        frame.unspawn();
        for (int i = 0; i < prognosis; i++)
            ((SimpleFigure) figures[i]).unspawn();
    }
}
