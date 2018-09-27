package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.figure.VirtualFigure;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class VirtualForecast extends AbstractForecast {

    public VirtualForecast(final IForecast forecast) {
        super(forecast.getDepth(), forecast.getFigureSize());
        for (int i = 0; i < prognosis; i++)
            figures[prognosis - 1 - i] = new VirtualFigure(forecast.getForecast(i));
    }

    public VirtualForecast(final int prognosis, final int size, final int difficulty, final RandomTypes randomType) {
        super(prognosis, size, difficulty, randomType);
        for (int i = 0; i < prognosis; i++)
            figures[i] = new VirtualFigure(size, difficulty, randomType.getRandomType());
    }

    @Override
    protected IFigure[] initFigures() {
        return new VirtualFigure[prognosis];
    }
}
