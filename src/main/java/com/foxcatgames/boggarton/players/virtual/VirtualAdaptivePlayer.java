package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.IAutomatedGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;

public class VirtualAdaptivePlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>, T extends AbstractVisualGame<B, F, G, P> & IAutomatedGame>
        extends AbstractVirtualAdaptivePlayer<B, F, G, P, T> {

    public VirtualAdaptivePlayer(final T game, final int prognosis, final IEater price) {
        super(game, prognosis, price, true);
    }

    @Override
    public String getName() {
        return "Путин"; // Путин адаптивный, но не эффективный
    }
}
