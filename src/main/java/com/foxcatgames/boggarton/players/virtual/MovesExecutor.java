package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.ReplayGame;
import com.foxcatgames.boggarton.game.figure.PredefinedFigure;
import com.foxcatgames.boggarton.game.forecast.PredefinedForecast;
import com.foxcatgames.boggarton.game.glass.ReplayGlass;

public class MovesExecutor extends AbstractMovesExecutor<Brick, PredefinedFigure, ReplayGlass, PredefinedForecast, ReplayGame> {

    private final char[] moves;

    public MovesExecutor(final ReplayGame game, final char[] moves) {
        super(game);
        this.moves = moves;

        final Thread thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(game.getName() + thread.getId());
        thread.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < moves.length && game.isGameOn(); i++)
                executeMove(moves[i]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
