package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.SimpleGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;

abstract public class AbstractVirtualNonAdaptivePlayer extends AbstractVirtualPlayer {

    public AbstractVirtualNonAdaptivePlayer(final AbstractGame game, final boolean moveDown) {
        super(game, "virtual player, non-adaptive, effective: " + moveDown, moveDown);
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
                game.checkCommand();
                game.getGlass().dropChanges();
                game.setMaxSpeed();
                ((SimpleGlass) game.getGlass()).waitChanges();
                break;

            // non-adaptive algorithm
            case NEXT:
                game.clearBuffer();
                game.setMaxSpeed();
                game.getBuffer();
                game.restoreSpeed();
                break;
            }
    }
}
