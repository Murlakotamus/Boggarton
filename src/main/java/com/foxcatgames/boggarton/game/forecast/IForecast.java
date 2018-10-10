package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public interface IForecast<B extends IBrick, F extends IFigure<B>> {

    int getDepth();
    int getFigureSize();
    int getDifficulty();
    RandomTypes getRandomType();

    F getForecast();
    F getForecast(int i);
}
