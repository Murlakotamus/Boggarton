package com.foxcatgames.boggarton.game.glass;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.utils.Changes;

abstract public class AbstractGlass<B extends IBrick, F extends AbstractFigure<B>> implements IGlass<B, F> {

    volatile protected boolean gameOver = false;
    protected int count = 0; // figures counter

    final protected Changes changes = new Changes(false);
    final protected GlassState<B, F> state = new GlassState<>();

    public AbstractGlass(final int width, final int height) {
        state.setWidth(width);
        state.setHeight(height);
    }

    @Override
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

    @Override
    public int getFullness() {
        return state.getFullness();
    }

    @Override
    public int processGlass() {
        while (findChainsToKill()) {
            killChains();
            while (removeHoles())
                ;
            addReaction();
        }
        return cleanReactions();
    }

    @Override
    public void killChains() {
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                if (brick(i, j) != null && brick(i, j).isKill())
                    removeBrick(i, j);
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    protected void setChanges(final boolean flag) {
        synchronized (changes) {
            changes.setFlag(flag);
            changes.notify();
        }
    }

    @Override
    public void waitChanges() {
        synchronized (changes) {
            try {
                while (!changes.isFlag() && !gameOver)
                    changes.wait();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            changes.notify();
        }
    }

    @Override
    public void dropChanges() {
        setChanges(false);
    }

    @Override
    public void setGameOver() {
        gameOver = true;
        setChanges(true);
    }

    @Override
    public int getReactions() {
        return state.getReactionLenght();
    }

    @Override
    public void addReaction() {
        state.addReaction();
    }

    @Override
    public int cleanReactions() {
        return state.cleanReactions();
    }

    @Override
    public boolean hasChanges() {
        return changes.isFlag();
    }

    @Override
    public GlassState<B, F> getGlassState() {
        return state;
    }

    @Override
    public B brick(final int i, final int j) {
        return state.getBrick(i, j);
    }

    @Override
    public F figure() {
        return state.getFigure();
    }

    @Override
    public int width() {
        return state.getWidth();
    }

    @Override
    public int height() {
        return state.getHeight();
    }

    @Override
    public boolean canMoveLeft() {
        return state.canMoveLeft();
    }

    @Override
    public boolean canMoveRight() {
        return state.canMoveRight();
    }

    @Override
    public int getSpaceLeft() {
        return state.getSpaceLeft();
    }

    @Override
    public int getSpaceRight() {
        return state.getSpaceRight();
    }

    @Override
    public int getCount() {
        return count;
    }
}
