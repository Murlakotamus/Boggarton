package stackup.players;

import stackup.game.AbstractGame;
import stackup.game.IForecast;
import stackup.game.IGlassState;
import stackup.game.utils.Pair;
import stackup.players.virtual.solver.Solver;

abstract public class VirtualPlayer implements Runnable {

    protected static final int LEFT = 0;
    protected static final int RIGHT = 1;
    protected static final int ROTATE = 2;
    protected static final int DOWN = 3;
    protected static final int NEXT = 4;
    
    protected final AbstractGame game;
    protected Solver solver;

    public VirtualPlayer(final AbstractGame game, final String name) {
        this.game = game;
        final Thread thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(game.getName() + ", " + name + ", " + thread.getId());
        thread.start();
    }
    
    protected void initPlayer() {
        solver = new Solver(game);
    }

    protected int[] getMoves(final int dept) {
        solver.initSolver(dept);
        return translateString(solver.getMoves());
    }

    protected final int[] translateString(final String moves) {
        final int len = moves.length();
        final int[] result = new int[len];

        for (int i = 0; i < len; i++)
            result[i] = (int) moves.charAt(i) - 65;

        return result;
    }

    public void run() {
        initPlayer();
        try {
            while (game.isGameOn()) {
                game.restoreSpeed();
                final Pair<IGlassState, IForecast> buffer = game.getBuffer();
                final int depth = buffer.getFirst().getFullness();
                final int[] moves = getMoves(depth);
                game.setMaxSpeed();
                if (moves.length > 0)
                    makeMoves(moves);
                else
                    game.clearBuffer();
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    
    abstract protected void makeMoves(final int... moves) throws InterruptedException;
}
