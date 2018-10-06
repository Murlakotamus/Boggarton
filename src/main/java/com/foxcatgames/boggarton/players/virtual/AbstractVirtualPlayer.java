package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.MultiplayerGame;
import com.foxcatgames.boggarton.game.forecast.IForecast;
import com.foxcatgames.boggarton.game.glass.IGlassState;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.players.IPlayer;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;
import com.foxcatgames.boggarton.players.virtual.solver.Solution;
import com.foxcatgames.boggarton.players.virtual.solver.Solver;

abstract public class AbstractVirtualPlayer extends AbstractExecutor implements IPlayer {

    private final Solver solver;
    private final IPrice price;

    public AbstractVirtualPlayer(final AbstractGame game, final String name, final IPrice price, final boolean moveDown) {
        super(game);
        this.price = price;
        solver = new Solver(game, moveDown, game.getForecast().getFigureSize());
        final Thread thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(game.getName() + ", " + thread.getId());
        thread.start();
    }

    protected char[] getMoves(final int dept) {
        final Solution solution = solver.getSolution(dept, price);
        Logger.log(Thread.currentThread().getName() + ", " + solution);
        return solution.getMoves();
    }

    public void run() {
        try {
            while (game.isGameOn()) {
                final Pair<IGlassState, IForecast> buffer = game.getBuffer();
                final IGlassState glassState = buffer.getFirst();
                if (glassState == null)
                    break;

                final int depth = glassState.getFullness();
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

    @Override
    public GameParams getGamesParams() {
        final GameParams.Builder builder = new GameParams.Builder();

        builder.setPrognosisDebth(game.getForecast().getDepth());
        builder.setFigureSize(game.getForecast().getFigureSize());
        builder.setScore(game.getGlass().getGlassState().getScore());
        builder.setPriceName(price.getName());
        builder.setSetSize(game.getForecast().getDifficulty());
        builder.setRandomName(game.getForecast().getRandomType().getName());
        builder.setCount(game.getGlass().getCount());
        builder.setPlayerName(getName());
        builder.setVirtual(true);

        if (game instanceof MultiplayerGame)
            builder.setYuckName(((MultiplayerGame) game).getYuckType().getName());

        return builder.build();
    }
}
