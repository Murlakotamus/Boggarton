package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

abstract public class AbstractVirtualNonAdaptivePlayer extends AbstractVirtualPlayer {

    public AbstractVirtualNonAdaptivePlayer(final AbstractGame game, final IPrice price, final boolean moveDown) {
        super(game, "virtual player, non-adaptive, effective: " + moveDown, price, moveDown);
    }

    @Override
    protected void makeMoves(final char... moves) throws InterruptedException {
        for (int i = 0; i < moves.length && game.isGameOn(); i++)
            switch (moves[i]) {
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
                        game.dropFigure();  // for log only
                    }
                });
                game.checkCommand();
                game.getGlass().dropChanges();
                game.setMaxSpeed();
                ((SimpleGlass) game.getGlass()).waitChanges();
                break;

            // non-adaptive algorithm
            case NEXT:
                game.waitNextFigure(); // for log only
                game.clearBuffer();
                game.getBuffer();
                game.restoreSpeed();
                break;
            }
    }
}
