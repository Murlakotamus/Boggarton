package com.foxcatgames.boggarton.players.virtual;

import static com.foxcatgames.boggarton.Const.NEXT;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.IAutomatedGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;

abstract public class AbstractVirtualAdaptivePlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>, T extends AbstractVisualGame<B, F, G, P> & IAutomatedGame>
        extends AbstractVirtualPlayer<B, F, G, P, T> {

    public AbstractVirtualAdaptivePlayer(final T game, final int prognosis, final IEater price, final boolean moveDown) {
        super(game, "virtual player, adaptive, effective: " + moveDown, prognosis, price, moveDown);
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
