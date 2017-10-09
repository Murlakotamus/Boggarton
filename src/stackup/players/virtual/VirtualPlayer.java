package stackup.players.virtual;

import stackup.game.AbstractGame;
import stackup.game.IForecast;
import stackup.game.IGlassState;
import stackup.game.utils.Pair;
import stackup.players.virtual.solver.Solution;
import stackup.players.virtual.solver.Solver;

abstract public class VirtualPlayer implements Runnable {

    private final boolean moveDown;
    protected final AbstractGame game;
    
    protected static final char LEFT = 'L';
    protected static final char RIGHT = 'R';
    protected static final char CYCLE = 'C';
    protected static final char DOWN = 'D';
    protected static final char NEXT = 'N';

    protected Solver solver;    

    public VirtualPlayer(final AbstractGame game, final String name, final boolean moveDown) {
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
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    abstract protected void makeMoves(final char... moves) throws InterruptedException;
}
