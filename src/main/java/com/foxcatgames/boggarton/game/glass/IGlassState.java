package com.foxcatgames.boggarton.game.glass;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.IFigure;

public interface IGlassState<B extends IBrick, F extends IFigure<B>> {

    int getI();
    int getJ();
    int getWidth();
    int getHeight();
    int getScore();
    int getNextPosition();
    int getFullness();

    F getFigure();
    B getBrick(int i, int j);
    IGlassState<B, F> getGlassState();

    void setNextPosition(int nextPosition);
    void setScore(int score);
    void setFigure(F figure);

    boolean canMoveLeft();
    boolean canMoveRight();
    int getSpaceLeft();
    int getSpaceRight();
}
