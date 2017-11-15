package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;

abstract public class AbstractExecutor implements Runnable {

    protected static final char LEFT = 'L';
    protected static final char RIGHT = 'R';
    protected static final char CYCLE = 'C';
    protected static final char DOWN = 'D';
    protected static final char NEXT = 'N';

    protected final AbstractGame game;

    public AbstractExecutor(final AbstractGame game) {
        this.game = game;
    }
}
