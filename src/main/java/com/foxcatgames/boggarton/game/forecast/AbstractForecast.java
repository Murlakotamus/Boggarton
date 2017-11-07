package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.game.figure.IFigure;

abstract public class AbstractForecast implements IForecast {

    final protected int prognosis; // dept
    final protected int size; // length of figure
    final protected IFigure[] figures;

    public AbstractForecast(final int prognosis, final int size) {
        this.prognosis = prognosis;
        this.size = size;
        figures = initFigures();
    }

    abstract protected IFigure[] initFigures();

    @Override
    public int getDepth() {
        return prognosis;
    }

    @Override
    public int getLenght() {
        return size;
    }

    @Override
    public IFigure getForecast() {
        return getForecast(0);
    }

    @Override
    public IFigure getForecast(final int i) {
        return figures[i];
    }
}
