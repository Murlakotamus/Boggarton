package stackup.players.virtual;

import stackup.game.AbstractGame;
import stackup.game.Glass;
import stackup.game.utils.Command;

abstract public class AbstractVirtualNonAdaptivePlayer extends VirtualPlayer {

    public AbstractVirtualNonAdaptivePlayer(final AbstractGame game, final boolean moveDown) {
        super(game, "virtual player, non-adaptive, effective: " + moveDown, moveDown);
    }

    @Override
    protected void makeMoves(final char... moves) throws InterruptedException {
        for (int i = 0; i < moves.length && game.isGameOn(); i++)
            switch (moves[i]) {
            case LEFT:
                game.sendCommand(new Command() {
                    @Override
                    public void execute() {
                        game.moveLeft();
                    }
                });
                break;

            case RIGHT:
                game.sendCommand(new Command() {
                    @Override
                    public void execute() {
                        game.moveRight();
                    }
                });
                break;

            case CYCLE:
                game.sendCommand(new Command() {
                    @Override
                    public void execute() {
                        game.rotateFigure();
                    }
                });
                break;

            case DOWN:
                game.checkCommand();
                game.getGlass().dropChanges();
                game.setMaxSpeed();
                ((Glass) game.getGlass()).waitChanges();
                break;

            // non-adaptive algorithm
            case NEXT:
                game.clearBuffer();
                game.setMaxSpeed();
                game.getBuffer();
                game.restoreSpeed();
                break;
            }
    }
}
