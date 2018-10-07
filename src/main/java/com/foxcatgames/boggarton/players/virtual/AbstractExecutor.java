package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.utils.ICommand;

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

    protected void executeMove(final char move) throws InterruptedException {
        switch (move) {
        case LEFT:
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.moveLeft();
                }
            });
            break;

        case RIGHT:
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.moveRight();
                }
            });
            break;

        case CYCLE:
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.rotateFigure();
                }
            });
            break;

        case DOWN:
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.dropFigure();
                }
            });
            game.getGlass().dropChanges();
            game.setMaxSpeed();
            game.getGlass().waitChanges();
            break;

        case NEXT:
            game.clearBuffer();
            game.getBuffer();
            game.restoreSpeed();
            break; // non-adaptive algorithm
        }
    }

}
