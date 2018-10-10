package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

public class VirtualAdaptivePlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>>
        extends AbstractVirtualAdaptivePlayer<B, F, G, P> {

    public VirtualAdaptivePlayer(final AbstractVisualGame<B, F, G, P> game, final IPrice price) {
        super(game, price, true);
    }

    public String getName() {
        return "Путин"; // Путин адаптивный, но не эффективный
    }
}
