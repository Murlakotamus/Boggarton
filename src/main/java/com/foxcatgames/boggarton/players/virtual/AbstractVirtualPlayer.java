package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.game.glass.IGlassState;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.players.IPlayer;
import com.foxcatgames.boggarton.players.virtual.solver.IPrice;
import com.foxcatgames.boggarton.players.virtual.solver.Solution;
import com.foxcatgames.boggarton.players.virtual.solver.Solver;

abstract public class AbstractVirtualPlayer<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>>
        extends AbstractExecutor<B, F, G, P> implements IPlayer {

    private final Solver<B, F, G, P> solver;
    private final IPrice price;

    public AbstractVirtualPlayer(final AbstractVisualGame<B, F, G, P> game, final String name, final IPrice price, final boolean moveDown) {
        super(game);
        this.price = price;
        solver = new Solver<>(game, moveDown, game.getForecast().getFigureSize());
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
                final Pair<IGlassState<B, F>, P> buffer = game.getBuffer();
                final IGlassState<B, F> glassState = buffer.getFirst();
                if (glassState == null)
                    break;

                final int depth = glassState.getFullness();
                final char[] moves = getMoves(depth);
                if (moves.length > 0)
                    makeVirtualPlayerMoves(moves);
                else
                    game.clearBuffer();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeVirtualPlayerMoves(final char[] moves) throws InterruptedException {
        for (int i = 0; i < moves.length && game.isGameOn(); i++)
            if (!executeVirtualPlayerMove(moves[i], i + 1 < moves.length && moves[i + 1] == NEXT))
                break;
    }

    protected boolean executeVirtualPlayerMove(final char move, final boolean finishTurn) throws InterruptedException {
        switch (move) {
        case DOWN:
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.dropFigure();
                    if (finishTurn)
                        game.waitNextFigure(); // for log only
                }
            });
            game.getGlass().dropChanges();
            game.setMaxSpeed();
            game.getGlass().waitChanges();
            break;
        default:
            super.executeMove(move);
        }
        return true;
    }

    @Override
    public GameParams getGameParams() {
        final GameParams.Builder builder = game.buildParams();

        builder.setPlayerName(getName());
        builder.setVirtual(true);
        return builder.build();
    }
}
