package com.foxcatgames.boggarton.game.figure;

import com.foxcatgames.boggarton.game.IBrick;

public interface IFigure {

    public void swapBricks(int i, int j);
    public void rotate();
    public void setNull(int i);
    public boolean isFallen();

    public int getFirstBrickNum();
    public int getLastBrickNum();

    public int getLenght();
    public int getNumber();
    public IBrick getBrick(int i);
}
