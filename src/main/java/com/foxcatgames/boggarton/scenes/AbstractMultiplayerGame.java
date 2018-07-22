package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_PAUSED;
import static com.foxcatgames.boggarton.Const.LOSER;
import static com.foxcatgames.boggarton.Const.WINNER;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.MultiplayerGame;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.game.utils.Victories;

abstract public class AbstractMultiplayerGame extends AbstractGameScene {

    protected static final int X = 90;

    protected static final String[] PLAYERS_NAMES = { "First", "Second" };

    protected final int numPlayers;
    private final SimpleEntity[] winners;
    private SimpleEntity loser;

    protected long pauseBetweenGames;
    protected MultiplayerGame[] game;
    private final SimpleEntity gamePaused[];

    AbstractMultiplayerGame(final SceneItem scene, final int width, final int height, final int[] forecast, final int length, final int numPlayers,
            YuckTypes yuckType, final int[] randomType) {
        super(scene);
        gamePaused = new SimpleEntity[numPlayers];
        for (int i = 0; i < numPlayers; i++)
            gamePaused[i] = new SimpleEntity(GAME_PAUSED, layer);

        if (width < length)
            throw new RuntimeException("Glass too narrow for figures");

        if (numPlayers > 2)
            throw new RuntimeException("Not implemented yet");

        if (numPlayers < 2)
            throw new RuntimeException("Not a multiplayer game");

        this.numPlayers = numPlayers;
        winners = new SimpleEntity[numPlayers];
        game = new MultiplayerGame[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            game[i] = new MultiplayerGame(layer, X + 446 * i, Y, width, height, Math.min(prognosis, forecast[i]), length, difficulty, Victories.getVictories(i),
                    yuckType, randomType); // FIXME => vic to player
            if (i < 4)
                game[i].setName(PLAYERS_NAMES[i]);
            else
                game[i].setName(i + " player");
        }

        for (int i = 0; i < numPlayers; i++) {
            game[1 - i].initLogger();
            game[1 - i].setEnemyGlass(game[i].getGlass());
        }

        for (int i = 0; i < numPlayers; i++) {
            game[i].initLogger();
            game[i].startGame();
        }
    }

    abstract protected void checkAuto();

    private void losersAndWinners(final int looserNumber) {
        for (int i = 0; i < numPlayers; i++) {
            if (i == looserNumber) {
                loser = new SimpleEntity(LOSER, layer);
                loser.spawn(new Vector2f(game[i].getX() + figureSize * BOX + 25, Y + BOX * 3 + BORDER));
            } else {
                winners[i] = new SimpleEntity(WINNER, layer);
                winners[i].spawn(new Vector2f(game[i].getX() + figureSize * BOX + 25, Y + BOX * 3 + BORDER));
                Victories.addVictory(i);
            }
            game[i].setGameOver();
            game[i].closeLogger();
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
        for (int i = 0; i < numPlayers; i++) {
            game[i].setGameOver();
            game[i].closeLogger();
        }
    }

    @Override
    protected void hideGlass() {
        for (int i = 0; i < numPlayers; i++) {
            ((SimpleGlass) game[i].getGlass()).pauseOn();
            gamePaused[i].spawn(new Vector2f(game[i].getX() + figureSize * BOX + 25, Y + BOX * 3 + BORDER));
        }
    }

    @Override
    protected void showGlass() {
        for (int i = 0; i < numPlayers; i++) {
            gamePaused[i].unspawn();
            ((SimpleGlass) game[i].getGlass()).pauseOff();
        }
    }
}
