package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;

abstract public class AbstractVirtualAdaptivePlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>>
        extends AbstractVirtualPlayer<B, F, G, P> {

    public AbstractVirtualAdaptivePlayer(final AbstractVisualGame<B, F, G, P> game, final IPrice price, final boolean moveDown) {
        super(game, "virtual player, adaptive, effective: " + moveDown, price, moveDown);
    }

    @Override
    protected String getSolution(final int dept) {
        String moves = super.getSolution(dept);
        int lastIndex = moves.indexOf(NEXT);
        if (lastIndex > 0)
            return moves.substring(0, lastIndex);
        Logger.err("Solver failed: " + moves);
        return moves;
    }
}
