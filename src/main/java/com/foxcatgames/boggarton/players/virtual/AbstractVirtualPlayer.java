package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.IForecast;
import com.foxcatgames.boggarton.game.IGlassState;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.players.virtual.solver.Solution;
import com.foxcatgames.boggarton.players.virtual.solver.Solver;

abstract public class AbstractVirtualPlayer implements Runnable {

    private final boolean moveDown;
    protected final AbstractGame game;

    protected static final char LEFT = 'L';
    protected static final char RIGHT = 'R';
    protected static final char CYCLE = 'C';
    protected static final char DOWN = 'D';
    protected static final char NEXT = 'N';

    protected Solver solver;

    public AbstractVirtualPlayer(final AbstractGame game, final String name, final boolean moveDown) {
        this.game = game;
        this.moveDown = moveDown;
        final Thread thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(game.getName() + ", " + name + ", " + thread.getId());
        thread.start();
    }

    protected void initPlayer() {
        solver = new Solver(game, moveDown);
    }

    protected char[] getMoves(final int dept) {
        solver.initSolver(dept);
        Solution solution = solver.getSolution();
        System.out.println(Thread.currentThread().getName() + ": " + solution);
        return solution.getMoves();
    }

    public void run() {
        initPlayer();
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
