package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

abstract public class AbstractForecast implements IForecast {

    final protected int prognosis; // dept
    final protected int size; // length of figure
    final protected IFigure[] figures;
    final protected int difficulty;
    final protected RandomTypes randomType;

    public AbstractForecast(final int prognosis, final int size) {
        this(prognosis, size, 0, null);
    }

    public AbstractForecast(final int prognosis, final int size, final int difficulties, final RandomTypes randomType) {
        this.prognosis = prognosis;
        this.size = size;
        this.difficulty = difficulties;
        this.randomType = randomType;
        figures = initFigures();
    }

    abstract protected IFigure[] initFigures();

    @Override
    public int getDepth() {
        return prognosis;
    }

    @Override
    public int getFigureSize() {
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

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public RandomTypes getRandomType() {
        return randomType;
    }
}
