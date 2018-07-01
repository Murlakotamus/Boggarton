package com.foxcatgames.boggarton.players.virtual;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
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

    private void makeMoves(final char... moves) throws InterruptedException {
        for (int i = 0; i < moves.length && game.isGameOn(); i++)
            switch (moves[i]) {
            case LEFT:
                game.sendCommand(new ICommand() {
                    @Override
                    public void execute() {
                        game.moveLeft();
                    }
                });
                break;

            case RIGHT:
                game.sendCommand(new ICommand() {
                    @Override
                    public void execute() {
                        game.moveRight();
                    }
                });
                break;

            case CYCLE:
                game.sendCommand(new ICommand() {
                    @Override
                    public void execute() {
                        game.rotateFigure();
                    }
                });
                break;

            case DOWN:
                game.sendCommand(new ICommand() {
                    @Override
                    public void execute() {
                        game.dropFigure();
                    }
                });
                game.getGlass().dropChanges();
                game.setMaxSpeed();
                ((SimpleGlass) game.getGlass()).waitChanges();
                break;

            case NEXT:
                game.clearBuffer();
                game.getBuffer();
                game.restoreSpeed();
                break;
            }
    }
}
