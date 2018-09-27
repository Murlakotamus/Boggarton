package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public interface IForecast {

    int getDepth();
    int getFigureSize();
    int getDifficulty();
    RandomTypes getRandomType();

    IFigure getForecast();
    IFigure getForecast(int i);
}
