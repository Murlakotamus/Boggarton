package com.foxcatgames.boggarton.game.glass;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.IFigure;

public interface IGlass<B extends IBrick, F extends IFigure<B>> {

    B brick(int i, int j);
    F figure();
    GlassState<B, F> getGlassState();
    int width();
    int height();
    void setFigure(int x, int y, boolean setChanges);
    int newFigure(F figure);

    int getSpaceLeft();
    int getSpaceRight();

    boolean canMoveLeft();
    boolean canMoveRight();

    void moveLeft();
    void moveRight();
    void rotate();

    void setChanges(int i, int x, int y);
    void setGameOver();
    boolean moveDown();
    boolean isGameOver();
    boolean findChainsToKill();
    void killChains();
    void removeBrick(int i, int j);
    int processGlass();
    boolean removeHoles();

    int getReactions();
    void addReaction();
    int cleanReactions();

    void dropChanges() throws InterruptedException;
    boolean hasChanges();
    void waitChanges() throws InterruptedException;

    int getFullness();
    int getCount();
}
