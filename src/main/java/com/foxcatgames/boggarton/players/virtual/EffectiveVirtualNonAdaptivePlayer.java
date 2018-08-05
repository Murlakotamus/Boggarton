package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

public class EffectiveVirtualNonAdaptivePlayer extends AbstractVirtualNonAdaptivePlayer {

    public EffectiveVirtualNonAdaptivePlayer(final AbstractGame game, final IPrice price) {
        super(game, price, false);
    }

    public String getName() {
        return "Улюкаев"; // Улюкаев эффективный, но не адаптивный
    }
}
