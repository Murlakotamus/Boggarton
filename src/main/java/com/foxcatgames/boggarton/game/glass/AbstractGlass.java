package com.foxcatgames.boggarton.game.glass;

import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.utils.Changes;

abstract public class AbstractGlass implements IGlass {

    volatile protected boolean gameOver = false;
    volatile protected int count = 0; // figures counter

    final protected Changes changes = new Changes(false);
    final protected GlassState state = new GlassState();

    public AbstractGlass(final int width, final int height) {
        state.setWidth(width);
        state.setHeight(height);
    }

    @Override
    public boolean findChainsToKill() {
        final int oldScore = state.getScore();
        for (int i = 1; i < state.getWidth() - 1; i++)
            for (int j = 0; j < state.getHeight(); j++)
                state.findHorizontals(i, j);

        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 1; j < state.getHeight() - 1; j++)
                state.findVerticals(i, j);

        for (int i = 1; i < state.getWidth() - 1; i++)
            for (int j = 1; j < state.getHeight() - 1; j++) {
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
    public void processGlass() {
        while (findChainsToKill()) {
            killChains();
            while (removeHoles())
                ;
            addReaction();
        }
        cleanReactions();
    }

    @Override
    public void killChains() {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++)
                if (state.getBrick(i, j) != null && state.getBrick(i, j).isKill())
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
            } catch (InterruptedException e) {
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
    public void cleanReactions() {
        state.cleanReactions();
    }

    @Override
    public boolean hasChanges() {
        return changes.isFlag();
    }

    @Override
    public GlassState getGlassState() {
        return state;
    }

    @Override
    public IFigure getFigure() {
        return state.getFigure();
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
