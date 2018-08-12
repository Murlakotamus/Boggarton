package com.foxcatgames.boggarton.game.figure;

import org.lwjgl.openal.AL10;

import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.game.IBrick;

abstract public class AbstractFigure implements IFigure {

    static final protected int REASON_TO_RORATE = 2;

    final protected IBrick[] bricks;
    final protected int lenght;

    protected int number; // number of bricks that still fall

    public AbstractFigure(int lenght) {
        this.lenght = lenght;
        bricks = new IBrick[lenght];
    }

    @Override
    public int getFirstBrickNum() {
        for (int i = 0; i < lenght; i++)
            if (bricks[i] != null)
                return i;
        return -1;
    }

    @Override
    public int getLastBrickNum() {
        for (int i = lenght - 1; i >= 0; i--)
            if (bricks[i] != null)
                return i;
        return -1;
    }

    @Override
    public void swapBricks(final int i, final int j) {
        final IBrick tmp = bricks[i];
        bricks[i] = bricks[j];
        bricks[j] = tmp;
    }

    @Override
    public IBrick getBrick(final int i) {
        return bricks[i];
    }

    @Override
    public void rotate() {
        if (number < REASON_TO_RORATE)
            return;

        final int firstBrick = getFirstBrickNum();
        if (firstBrick == -1)
            return;

        for (int i = firstBrick + 1; i < lenght; i++)
            if (bricks[i] != null)
                swapBricks(i, firstBrick);
    }

    @Override
    public void setNull(final int i, final int set, final boolean silent) {
        if (i < 0 || i >= lenght || bricks[i] == null)
            return;

        bricks[i] = null;
        number--;

        if (silent)
            return;

        int sound;
        switch (set) {
        case 2:
            sound = 2 + i;
            AL10.alSourcePlay(Sound.source.get(sound));
            break;
        case 3:
            sound = 7 + i;
            AL10.alSourcePlay(Sound.source.get(sound));
            break;
        case 4:
            sound = 12 + i;
            AL10.alSourcePlay(Sound.source.get(sound));
            break;
        }

    }

    @Override
    public boolean isFallen() {
        return number == 0;
    }

    @Override
    public int getLenght() {
        return lenght;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            if (bricks[i] == null)
                sb.append('x');
            else
                sb.append(bricks[i].getType() - 10);
        }
        sb.append('\n');

        return sb.toString();
    }
}
