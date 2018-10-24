package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;

public class EffectiveVirtualAdaptivePlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>>
        extends AbstractVirtualAdaptivePlayer<B, F, G, P> {

    public EffectiveVirtualAdaptivePlayer(final AbstractVisualGame<B, F, G, P> game, final int prognosis, final IEater price) {
        super(game, prognosis, price, false);
    }

    public String getName() {
        return "Чубайс"; // Чубайс эффективный и адаптивный
    }
}
