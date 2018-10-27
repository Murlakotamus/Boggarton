package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.AutomatedGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.players.IPlayer;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;
import com.foxcatgames.boggarton.players.virtual.solver.Solver;

abstract public class AbstractVirtualPlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>, T extends AbstractVisualGame<B, F, G, P> & AutomatedGame>
        extends AbstractMovesExecutor<B, F, G, P, T> implements IPlayer {

    private final IEater eater;
    private final boolean moveDown;
    private final int prognosis;

    public AbstractVirtualPlayer(final T game, final String name, final int prognosis, final IEater eater,
            final boolean moveDown) {
        super(game);
        this.prognosis = prognosis;
        this.eater = eater;
        this.moveDown = moveDown;
        final Thread thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(name + ", " + thread.getId());
        thread.start();
    }

    protected String getMoves(final int depth) {
        final String moves = getSolution(depth);
        Logger.log(Thread.currentThread().getName() + ", " + moves);
        return moves;
    }

    protected String getSolution(final int depth) {
        final Solver<B, F, G, P> solver = new Solver<>(game, depth, moveDown, game.getForecast().getFigureSize(), eater);
        return solver.getSolution().getMoves();
    }

    @Override
    public void run() {
        try {
            while (game.isGameOn()) {
                final Pair<GlassState<B, F>, P> buffer = game.getBuffer();
                final GlassState<B, F> glassState = buffer.getFirst();
                if (glassState == null)
                    break;

                final int fullness = glassState.getFullness();
                final int forecast = buffer.getSecond().getDepth();
                final int depth = Math.min(Math.min(prognosis, forecast), fullness);
                final char[] moves = getMoves(depth).toCharArray();

                if (moves.length > 0)
                    makeVirtualPlayerMoves(moves);
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeVirtualPlayerMoves(final char[] moves) throws InterruptedException {
        for (int i = 0; i < moves.length && game.isGameOn(); i++)
            executeMove(moves[i]);
    }

    @Override
    public GameParams getGameParams() {
        final GameParams.Builder builder = game.buildParams();

        builder.setPlayerName(getName());
        builder.setVirtual(true);
        return builder.build();
    }
}
