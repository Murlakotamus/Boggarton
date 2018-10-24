package com.foxcatgames.boggarton.game.figure;

import com.foxcatgames.boggarton.game.IBrick;

abstract public class AbstractFigure<B extends IBrick> {

    final protected B[] bricks;
    final protected int figureSize;

    protected int number; // number of bricks that still fall

    public AbstractFigure(B[] bricks) {
        figureSize = bricks.length;
        number = figureSize;
        this.bricks = bricks;
    }

    public int getFirstBrickNum() {
        for (int i = 0; i < figureSize; i++)
            if (bricks[i] != null)
                return i;
        return -1;
    }

    public int getLastBrickNum() {
        for (int i = figureSize - 1; i >= 0; i--)
            if (bricks[i] != null)
                return i;
        return -1;
    }

    public void swapBricks(final int i, final int j) {
        final B tmp = bricks[i];
        bricks[i] = bricks[j];
        bricks[j] = tmp;
    }

    public B getBrick(final int i) {
        return bricks[i];
    }

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

    public void setNull(final int i) {
        if (i < 0 || i >= figureSize || bricks[i] == null)
            return;

        bricks[i] = null;
        number--;
    }

    public boolean isFallen() {
        return number == 0;
    }

    public int getLenght() {
        return figureSize;
    }

    public int getNumber() {
        return number;
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < figureSize; i++) {
            if (bricks[i] == null)
                sb.append('x');
            else
                sb.append((char) (bricks[i].getType() + 54));
        }
        sb.append('\n');

        return sb.toString();
    }
}
