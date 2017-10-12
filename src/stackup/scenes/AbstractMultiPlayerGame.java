package stackup.scenes;

import static stackup.Const.BORDER;
import static stackup.Const.GAME_PAUSED;
import static stackup.Const.LOSER;
import static stackup.Const.BOX;
import static stackup.Const.WINNER;

import org.lwjgl.util.vector.Vector2f;

import stackup.entity.SimpleEntity;
import stackup.game.Glass;
import stackup.game.MultiPlayerGame;
import stackup.game.Stage;
import stackup.game.utils.Victories;

abstract public class AbstractMultiPlayerGame extends AbstractGameScene {

    protected static final int X = 90;

    protected static final String[] PLAYERS_NAMES = { "First", "Second", "Third" };

    protected final int numPlayers;
    private final SimpleEntity[] winners;
    private SimpleEntity loser;

    protected long pauseBetweenGames;
    protected MultiPlayerGame[] game;
    private final SimpleEntity gamePaused[];

    AbstractMultiPlayerGame(final Scene scene, final int width, final int height,
            final int[] forecast, final int length, final int numPlayers) {
        super(scene);
        gamePaused = new SimpleEntity[numPlayers];
        for (int i = 0; i < numPlayers; i++)
            gamePaused[i] = new SimpleEntity(GAME_PAUSED, layer);

        if (width < length)
            throw new RuntimeException("Glass too small for figures");

        if (numPlayers > 2)
            throw new RuntimeException("Not implemented yet");

        if (numPlayers < 2)
            throw new RuntimeException("Not a multiplayer game");

        this.numPlayers = numPlayers;
        winners = new SimpleEntity[numPlayers];
        game = new MultiPlayerGame[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            game[i] = new MultiPlayerGame(layer, X + 450 * i, Y, width, height,
                    Math.min(prognosis, forecast[i]), length, difficulty,
                    Victories.getVictories(i)); // FIXME => vic to player
            if (i < 4)
                game[i].setName(PLAYERS_NAMES[i]);
            else
                game[i].setName(i + " player");
            game[i].startGame();
        }
    }

    abstract protected void checkAuto();

    private void losersAndWinners(final int looserNumber) {
        for (int i = 0; i < numPlayers; i++) {
            if (i == looserNumber) {
                loser = new SimpleEntity(LOSER, layer);
                loser.spawn(
                        new Vector2f(game[i].getX() + size * 30 + 25, Y + BOX * 3 + BORDER));
            } else {
                winners[i] = new SimpleEntity(WINNER, layer);
                winners[i].spawn(
                        new Vector2f(game[i].getX() + size * 30 + 25, Y + BOX * 3 + BORDER));
                Victories.addVictory(i);
            }
            game[i].setGameOver();
        }
    }

    @Override
    protected void changes() {
        for (int i = 0; i < numPlayers; i++)
            if (game[i].isGameOver() && loser == null) {
                losersAndWinners(i);
                pauseBetweenGames = System.currentTimeMillis();
            }

        checkAuto();

        if (loser != null)
            return;

        for (int i = 0; i < numPlayers; i++) {
            game[i].processStage();
            if (game[i].getStages() == Stage.COMPRESS)
                getYucks(i);
        }
    }

    private void getYucks(final int n) {
        for (int j = 0; j < numPlayers; j++)
            if (n != j)
                game[n].addYuck(game[j].getYuckForEnemy());
    }

    @Override
    protected void setGameOver() {
        super.setGameOver();
        for (int i = 0; i < numPlayers; i++)
            game[i].setGameOver();
    }

    @Override
    protected void hideGlass() {
        for (int i = 0; i < numPlayers; i++) {
            ((Glass) game[i].getGlass()).pauseOn();
            gamePaused[i].spawn(
                    new Vector2f(game[i].getX() + size * 30 + 25, Y + BOX * 3 + BORDER));
        }
    }

    @Override
    protected void showGlass() {
        for (int i = 0; i < numPlayers; i++) {
            gamePaused[i].unspawn();
            ((Glass) game[i].getGlass()).pauseOff();
        }
    }
}
