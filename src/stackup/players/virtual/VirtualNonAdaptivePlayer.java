package stackup.players.virtual;

import stackup.game.AbstractGame;
import stackup.game.Glass;
import stackup.game.utils.Command;
import stackup.players.VirtualPlayer;

public class VirtualNonAdaptivePlayer extends VirtualPlayer {

    public VirtualNonAdaptivePlayer(final AbstractGame game) {
        super(game, "virtual player, non-adaptive");
    }

    @Override
    protected void makeMoves(final int... moves) throws InterruptedException {
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

            case ROTATE:
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
                ((Glass)game.getGlass()).waitChanges();
                break;

            case NEXT:
                game.clearBuffer();
                game.getBuffer();
                break; // non-adaptive algorithm
            }
    }
}
