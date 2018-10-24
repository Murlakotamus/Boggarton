package com.foxcatgames.boggarton.game.forecast;

import java.util.List;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.VirtualBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.figure.VirtualFigure;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class VirtualForecast extends AbstractForecast<VirtualBrick, VirtualFigure> {

    public <B extends IBrick, F extends AbstractFigure<B>> VirtualForecast(final AbstractForecast<B, F> forecast) {
        super(new VirtualFigure[forecast.getDepth()]);
        for (int i = 0; i < forecast.getDepth(); i++)
            figures[i] = new VirtualFigure(forecast.getForecast(i));
    }

    public VirtualForecast(final int prognosis, final int size, final int difficulty, final RandomTypes randomType) {
        super(new VirtualFigure[prognosis], difficulty, randomType);
        for (int i = 0; i < prognosis; i++)
            figures[i] = new VirtualFigure(size, difficulty, randomType.getRandomType());
    }

    @Override
    public void setNext(final List<Pair<Integer, Integer>> pairs) {
    }
}
