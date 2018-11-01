package com.foxcatgames.boggarton.players.virtual;

import static com.foxcatgames.boggarton.Const.DOWN;
import static com.foxcatgames.boggarton.Const.LEFT;
import static com.foxcatgames.boggarton.Const.NEXT;
import static com.foxcatgames.boggarton.Const.RIGHT;
import static com.foxcatgames.boggarton.Const.UP;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.IAutomatedGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;

abstract public class AbstractMovesExecutor<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>, T extends AbstractVisualGame<B, F, G, P> & IAutomatedGame>
        implements Runnable {

    protected final T game;

    public AbstractMovesExecutor(final T game) {
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
            game.getGlass().waitChanges();
            break;

        case RIGHT:
            game.restoreSpeed();
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.moveRight();

                }
            });
            game.getGlass().waitChanges();
            break;

        case UP:
            game.restoreSpeed();
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.rotateFigure();
                }
            });
            game.getGlass().waitChanges();
            break;

        case DOWN:
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.dropFigure();
                    game.setMaxSpeed();

                }
            });
            game.getGlass().waitChanges();
            game.restoreSpeed();
            break;

        case NEXT:
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.finishTurn();
                    game.restoreSpeed();
                }
            });
            game.getGlass().waitChanges();
            game.clearBuffer(); // MoveExecutor and NonAdaptivePlayer must wait for buffer update
            game.getBuffer();
            break;
        }
    }
}