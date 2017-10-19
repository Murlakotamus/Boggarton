package stackup.players.virtual;

import stackup.game.AbstractGame;
import stackup.game.SimpleGlass;
import stackup.game.utils.Command;

abstract public class AbstractVirtualAdaptivePlayer extends AbstractVirtualPlayer {

    public AbstractVirtualAdaptivePlayer(final AbstractGame game, final boolean moveDown) {
        super(game, "virtual player, adaptive, effective: " + moveDown, moveDown);
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
                ((SimpleGlass) game.getGlass()).waitChanges();
                break;

            // adaptive algorithm
            case NEXT:
                game.clearBuffer();
                game.getBuffer();
                game.restoreSpeed();
                return;
            }
    }
}
