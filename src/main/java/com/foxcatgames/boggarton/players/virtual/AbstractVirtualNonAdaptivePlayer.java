package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.AutomatedGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;

abstract public class AbstractVirtualNonAdaptivePlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>, T extends AbstractVisualGame<B, F, G, P> & AutomatedGame>
        extends AbstractVirtualPlayer<B, F, G, P, T> {

    public AbstractVirtualNonAdaptivePlayer(final T game, final int prognosis, final IEater price, final boolean moveDown) {
        super(game, "virtual player, non-adaptive, effective: " + moveDown, prognosis, price, moveDown);
    }
}
