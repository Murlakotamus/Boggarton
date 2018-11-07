package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.IAutomatedGame;
import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractForecast;
import com.foxcatgames.boggarton.game.glass.AbstractGlass;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;

abstract public class AbstractVirtualNonAdaptivePlayer<B extends IBrick, F extends AbstractFigure<B>, G extends AbstractGlass<B, F>, P extends AbstractForecast<B, F>, T extends IAutomatedGame<B, F, G, P>>
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
