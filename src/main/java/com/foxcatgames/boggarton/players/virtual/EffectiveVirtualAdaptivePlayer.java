package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

public class EffectiveVirtualAdaptivePlayer extends AbstractVirtualAdaptivePlayer {

    public EffectiveVirtualAdaptivePlayer(final AbstractGame game, final IPrice price) {
        super(game, price, false);
    }
}
