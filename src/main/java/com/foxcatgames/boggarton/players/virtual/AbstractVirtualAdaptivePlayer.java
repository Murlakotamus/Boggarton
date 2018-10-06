package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

abstract public class AbstractVirtualAdaptivePlayer extends AbstractVirtualPlayer {

    public AbstractVirtualAdaptivePlayer(final AbstractGame game, final IPrice price, final boolean moveDown) {
        super(game, "virtual player, adaptive, effective: " + moveDown, price, moveDown);
    }

    @Override
    protected boolean executeMove(final char move, final boolean finishTurn)  throws InterruptedException {
            switch (move) {
            // adaptive algorithm
            case NEXT:
                game.clearBuffer();
                game.getBuffer();
                game.restoreSpeed();
                return false;
            default:
                return super.executeMove(move, finishTurn);
            }
    }
}
