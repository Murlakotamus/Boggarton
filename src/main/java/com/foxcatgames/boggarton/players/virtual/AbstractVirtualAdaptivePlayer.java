package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.SimpleGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;

abstract public class AbstractVirtualAdaptivePlayer extends AbstractVirtualPlayer {

    public AbstractVirtualAdaptivePlayer(final AbstractGame game, final boolean moveDown) {
        super(game, "virtual player, adaptive, effective: " + moveDown, moveDown);
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
                        game.dropFigure(); // only for log
                    }
                });
                game.checkCommand();
                game.getGlass().dropChanges();
                game.setMaxSpeed();
                ((SimpleGlass) game.getGlass()).waitChanges();
                break;

            // adaptive algorithm
            case NEXT:
                game.waitNextFigure(); // only for log
                game.clearBuffer();
                game.getBuffer();
                game.restoreSpeed();
                return;
            }
    }
}
