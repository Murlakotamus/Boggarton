package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.IAutomatedGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;

abstract public class AbstractVirtualNonAdaptivePlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>, T extends IAutomatedGame<B, F, G, P>>
        extends AbstractVirtualPlayer<B, F, G, P, T> {

    public AbstractVirtualNonAdaptivePlayer(final T game, final int prognosis, final IEater price, final boolean moveDown) {
        super(game, "virtual player, non-adaptive, effective: " + !moveDown, prognosis, price, moveDown);
    }

    @Override
    protected void makeVirtualPlayerMoves(final char[] moves) throws InterruptedException {
        for (int i = 0; i < moves.length && game.isGameOn(); i++) {
            executeMove(moves[i]);
            if (game.isYuckHappened() && moves[i] == Const.NEXT) {
                game.dropYuckHappened();
                break;
            }
        }
    }
}
