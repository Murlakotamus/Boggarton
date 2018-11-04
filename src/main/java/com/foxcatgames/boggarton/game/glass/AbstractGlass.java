package com.foxcatgames.boggarton.game.glass;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.utils.Changes;

abstract public class AbstractGlass<B extends IBrick, F extends AbstractFigure<B>> {

    volatile protected boolean gameOver = false;
    protected int count = 0; // figures counter

    final protected Changes changes = new Changes();
    final protected GlassState<B, F> state;

    public AbstractGlass(final int width, final int height, final int nextPosition) {
        state = new GlassState<>(nextPosition);
        state.setWidth(width);
        state.setHeight(height);
    }

    abstract public boolean removeHoles();
    abstract public void removeBrick(int i, int j);
    abstract public int newFigure(F newFigure);
    abstract public void rotate();
    abstract public void moveLeft();
    abstract public void moveRight();

    public boolean findChainsToKill() {
        final int oldScore = state.getScore();
        for (int i = 1; i < width() - 1; i++)
            for (int j = 0; j < height(); j++)
                state.findHorizontals(i, j);

        for (int i = 0; i < width(); i++)
            for (int j = 1; j < height() - 1; j++)
                state.findVerticals(i, j);

        for (int i = 1; i < width() - 1; i++)
            for (int j = 1; j < height() - 1; j++) {
                state.findMainDiags(i, j);
                state.findAntiDiags(i, j);
            }
        return oldScore < state.getScore();
    }

    public int getFullness() {
        return state.getFullness();
    }

    public int processGlass() {
        while (findChainsToKill()) {
            killChains();
            while (removeHoles())
                ;
            addReaction();
        }
        return cleanReactions();
    }

    public void killChains() {
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                if (brick(i, j) != null && brick(i, j).isKill())
                    removeBrick(i, j);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setChanges(final boolean flag) {
        synchronized (changes) {
            while (flag == changes.isFlag())
                try {
                    changes.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            changes.setFlag(flag);
            changes.notify();
        }
    }

    public void waitChanges() throws InterruptedException {
        synchronized (changes) {
            while (!changes.isFlag() && !gameOver)
                changes.wait();
            changes.setFlag(false);
            changes.notify();
        }
    }

    public void dropChanges() {
        changes.setFlag(false);
    }

    public void setGameOver() {
        gameOver = true;
        synchronized (changes) {
            changes.setFlag(true);
            changes.notify();
        }
    }

    public int getReactions() {
        return state.getReactionLenght();
    }

    public void addReaction() {
        state.addReaction();
    }

    public int cleanReactions() {
        return state.cleanReactions();
    }

    public boolean hasChanges() {
        return changes.isFlag();
    }

    public GlassState<B, F> getGlassState() {
        return state;
    }

    public B brick(final int i, final int j) {
        return state.getBrick(i, j);
    }

    public F figure() {
        return state.getFigure();
    }

    public int width() {
        return state.getWidth();
    }

    public int height() {
        return state.getHeight();
    }

    public boolean canMoveLeft() {
        return state.canMoveLeft();
    }

    public boolean canMoveRight() {
        return state.canMoveRight();
    }

    public int getSpaceLeft() {
        return state.getSpaceLeft();
    }

    public int getSpaceRight() {
        return state.getSpaceRight();
    }

    public int getCount() {
        return count;
    }
}
