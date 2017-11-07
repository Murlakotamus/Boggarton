package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.forecast.IForecast;
import com.foxcatgames.boggarton.game.glass.IGlassState;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.players.virtual.solver.Solution;
import com.foxcatgames.boggarton.players.virtual.solver.Solver;

abstract public class AbstractVirtualPlayer extends AbstractExecutor implements Runnable {

    protected Solver solver;

    public AbstractVirtualPlayer(final AbstractGame game, final String name, final boolean moveDown) {
        super(game);
        solver = new Solver(game, moveDown);
        final Thread thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(game.getName() + ", " + name + ", " + thread.getId());
        thread.start();
    }

    protected char[] getMoves(final int dept) {
        solver.initSolver(dept);
        Solution solution = solver.getSolution();
        return solution.getMoves();
    }

    public void run() {
        try {
            while (game.isGameOn()) {
                final Pair<IGlassState, IForecast> buffer = game.getBuffer();
                final int depth = buffer.getFirst().getFullness();
                final char[] moves = getMoves(depth);
                if (moves.length > 0)
                    makeMoves(moves);
                else
                    game.clearBuffer();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    abstract protected void makeMoves(final char... moves) throws InterruptedException;
}
