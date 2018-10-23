package com.foxcatgames.boggarton.game.figure;

import com.foxcatgames.boggarton.game.IBrick;

abstract public class AbstractFigure<B extends IBrick> implements IFigure<B> {

    final protected B[] bricks;
    final protected int figureSize;

    protected int number; // number of bricks that still fall

    public AbstractFigure(B[] bricks) {
        figureSize = bricks.length;
        number = figureSize;
        this.bricks = bricks;
    }

    @Override
    public int getFirstBrickNum() {
        for (int i = 0; i < figureSize; i++)
            if (bricks[i] != null)
                return i;
        return -1;
    }

    @Override
    public int getLastBrickNum() {
        for (int i = figureSize - 1; i >= 0; i--)
            if (bricks[i] != null)
                return i;
        return -1;
    }

    @Override
    public void swapBricks(final int i, final int j) {
        final B tmp = bricks[i];
        bricks[i] = bricks[j];
        bricks[j] = tmp;
    }

    @Override
    public B getBrick(final int i) {
        return bricks[i];
    }

    @Override
    public void rotate() {
        if (number < 2)
            return;

        final int firstBrick = getFirstBrickNum();
        if (firstBrick == -1)
            return;

        for (int i = firstBrick + 1; i < figureSize; i++)
            if (bricks[i] != null)
                swapBricks(i, firstBrick);
    }

    @Override
    public void setNull(final int i) {
        if (i < 0 || i >= figureSize || bricks[i] == null)
            return;

        bricks[i] = null;
        number--;
    }

    @Override
    public boolean isFallen() {
        return number == 0;
    }

    @Override
    public int getLenght() {
        return figureSize;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < figureSize; i++) {
            if (bricks[i] == null)
                sb.append('x');
            else
                sb.append(bricks[i].getType() - 10);
        }
        sb.append('\n');

        return sb.toString();
    }
}
