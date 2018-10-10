package com.foxcatgames.boggarton.players.virtual;

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
    protected boolean executeVirtualPlayerMove(final char move, final boolean finishTurn) throws InterruptedException {
        switch (move) {
        case NEXT:
            game.clearBuffer();
            game.getBuffer();
            game.restoreSpeed();
            return false; // adaptive algorithm
        default:
            return super.executeVirtualPlayerMove(move, finishTurn);
        }
    }
}
