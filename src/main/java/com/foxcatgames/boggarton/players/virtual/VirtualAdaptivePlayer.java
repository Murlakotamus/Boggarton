package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

public class VirtualAdaptivePlayer extends AbstractVirtualAdaptivePlayer {

    public VirtualAdaptivePlayer(final AbstractGame game, final IPrice price) {
        super(game, price, true);
    }

    public String getName() {
        return "Путин"; // Путин адаптивный, но не эффективный
    }
}
