package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;

public class MovesExecutor extends AbstractExecutor implements Runnable {

    private final char[] moves;

    public MovesExecutor(final AbstractGame game, final char[] moves) {
        super(game);
        this.moves = moves;

        final Thread thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(game.getName() + thread.getId());
        thread.start();
    }

    public void run() {
        try {
            for (int i = 0; i < moves.length && game.isGameOn(); i++)
                executeMove(moves[i]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
