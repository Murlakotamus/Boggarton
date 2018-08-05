package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.game.figure.IFigure;

public interface IForecast {

    int getDepth();
    int getFigureSize();

    IFigure getForecast();
    IFigure getForecast(int i);
}
