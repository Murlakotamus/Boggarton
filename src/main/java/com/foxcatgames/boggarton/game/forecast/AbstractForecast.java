package com.foxcatgames.boggarton.game.forecast;

import java.util.List;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

abstract public class AbstractForecast<B extends IBrick, F extends AbstractFigure<B>> implements IForecast<B, F> {

    final protected F[] figures;
    final protected int difficulty;
    final protected RandomTypes randomType;

    abstract public void setNext(final List<Pair<Integer, Integer>> pairs);

    public AbstractForecast(final F[] figures) {
        this(figures, 0, null);
    }

    public AbstractForecast(final F[] figures, final int difficulties, final RandomTypes randomType) {
        this.figures = figures;
        this.difficulty = difficulties;
        this.randomType = randomType;
    }

    @Override
    public int getDepth() {
        return figures.length;
    }

    @Override
    public int getFigureSize() {
        return figures[0].getLenght();
    }

    @Override
    public F getForecast() {
        return getForecast(0);
    }

    @Override
    public F getForecast(final int i) {
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

    @Override
    public String toString() {
        String result = "Forecast:\n";
        for (F figure : figures)
            result += figure;
        return result;
    }
}
