package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.IAutomatedGame;
import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractForecast;
import com.foxcatgames.boggarton.game.glass.AbstractGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;

public class EffectiveVirtualNonAdaptivePlayer<B extends IBrick, F extends AbstractFigure<B>, G extends AbstractGlass<B, F>, P extends AbstractForecast<B, F>, T extends IAutomatedGame<B, F, G, P>>
        extends AbstractVirtualNonAdaptivePlayer<B, F, G, P, T> {

    public EffectiveVirtualNonAdaptivePlayer(final T game, final int prognosis, final IEater price) {
        super(game, prognosis, price, false);
    }

    @Override
    public String getName() {
        return "Улюкаев"; // Улюкаев эффективный, но не адаптивный
    }
}
