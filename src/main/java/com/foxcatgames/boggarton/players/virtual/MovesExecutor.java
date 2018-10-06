package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.utils.ICommand;

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
            makeMoves(moves);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean executeMove(final char move, final boolean finishTurn) throws InterruptedException {
        switch (move) {
        case DOWN:
            game.sendCommand(new ICommand() {
                @Override
                public void execute() {
                    game.dropFigure();
                }
            });
            game.getGlass().dropChanges();
            game.setMaxSpeed();
            game.getGlass().waitChanges();
            return true;
        default:
            return super.executeMove(move, finishTurn);
        }
    }
}
