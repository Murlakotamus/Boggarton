package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;

abstract public class AbstractMovesExecutor<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>>
        implements Runnable {

    protected static final char LEFT = 'L';
    protected static final char RIGHT = 'R';
    protected static final char CYCLE = 'C';
    protected static final char DOWN = 'D';
    protected static final char NEXT = 'N';

    protected final AbstractVisualGame<B, F, G, P> game;

    public AbstractMovesExecutor(final AbstractVisualGame<B, F, G, P> game) {
        this.game = game;
    }

    protected void executeMove(final char move) throws InterruptedException {
        switch (move) {
        case LEFT:
            game.restoreSpeed();
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.moveLeft();
                }
            });
            break;

        case RIGHT:
            game.restoreSpeed();
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.moveRight();
                }
            });
            break;

        case CYCLE:
            game.restoreSpeed();
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.rotateFigure();
                }
            });
            break;

        case DOWN:
            game.restoreSpeed();
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
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.finishTurn();
                }
            });
            game.clearBuffer(); // MoveExecutor and NonAdaptivePlayer must wait for buffer update
            game.getBuffer();
            game.restoreSpeed();
            break;
        }
    }
}
