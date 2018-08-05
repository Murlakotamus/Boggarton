package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

public class VirtualNonAdaptivePlayer extends AbstractVirtualNonAdaptivePlayer {

    public VirtualNonAdaptivePlayer(final AbstractGame game, final IPrice price) {
        super(game, price, true);
    }

    public String getName() {
        return "Медведев"; // Медведев не эффективный и не адаптивный
    }

}
