package com.foxcatgames.boggarton.game.figure;

import com.foxcatgames.boggarton.game.IBrick;

public interface IFigure<B extends IBrick> {

    void swapBricks(int i, int j);
    void rotate();
    void setNull(int i);
    boolean isFallen();

    int getFirstBrickNum();
    int getLastBrickNum();

    int getLenght();
    int getNumber();
    B getBrick(int i);
}
