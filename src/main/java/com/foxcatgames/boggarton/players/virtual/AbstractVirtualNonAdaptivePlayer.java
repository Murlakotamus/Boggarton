package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

abstract public class AbstractVirtualNonAdaptivePlayer extends AbstractVirtualPlayer {

    public AbstractVirtualNonAdaptivePlayer(final AbstractGame game, final IPrice price, final boolean moveDown) {
        super(game, "virtual player, non-adaptive, effective: " + moveDown, price, moveDown);
    }
}
