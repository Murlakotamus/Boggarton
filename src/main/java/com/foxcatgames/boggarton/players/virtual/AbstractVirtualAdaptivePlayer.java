package com.foxcatgames.boggarton.players.virtual;

import static com.foxcatgames.boggarton.Const.NEXT;

import com.foxcatgames.boggarton.game.IAutomatedGame;
import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractForecast;
import com.foxcatgames.boggarton.game.glass.AbstractGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;

abstract public class AbstractVirtualAdaptivePlayer<B extends IBrick, F extends AbstractFigure<B>, G extends AbstractGlass<B, F>, P extends AbstractForecast<B, F>, T extends IAutomatedGame<B, F, G, P>>
        extends AbstractVirtualPlayer<B, F, G, P, T> {

    public AbstractVirtualAdaptivePlayer(final T game, final int prognosis, final IEater price, final boolean moveDown) {
        super(game, "virtual player, adaptive, effective: " + !moveDown, prognosis, price, moveDown);
    }

    @Override
    protected String getSolution(final int depth) {
        final String moves = super.getSolution(depth);
        final int lastIndex = moves.indexOf(NEXT);
        String result = moves;
        if (lastIndex > 0)
            result = moves.substring(0, lastIndex + 1);
        return result;
    }
}
