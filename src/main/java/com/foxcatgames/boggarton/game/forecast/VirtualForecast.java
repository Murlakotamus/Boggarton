package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.figure.VirtualFigure;

public class VirtualForecast extends AbstractForecast {

    public VirtualForecast(final IForecast forecast) {
        super(forecast.getDepth(), forecast.getLenght());
        for (int i = 0; i < prognosis; i++)
            figures[prognosis - 1 - i] = new VirtualFigure(forecast.getForecast(i));
    }

    @Override
    protected IFigure[] initFigures() {
        return new VirtualFigure[prognosis];
    }
}
