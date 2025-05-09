package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_PAUSED;
import static com.foxcatgames.boggarton.Const.LOSER;
import static com.foxcatgames.boggarton.Const.WINNER;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.AbstractMultiplayerGame;
import com.foxcatgames.boggarton.game.utils.DbHandler;
import com.foxcatgames.boggarton.game.utils.Victories;
import com.foxcatgames.boggarton.players.IPlayer;

import java.util.Objects;

abstract public class AbstractMultiplayerScene extends AbstractPlayingScene {

    protected static final int X = 90;
    protected static final int PLAYERS = 2;

    protected static final String[] PLAYERS_NAMES = { "First", "Second" };

    private final SimpleEntity winner = new SimpleEntity(WINNER, layer);
    private final SimpleEntity loser = new SimpleEntity(LOSER, layer);
    private final SimpleEntity[] gamePaused = new SimpleEntity[PLAYERS];

    protected IPlayer leftPlayer;
    protected IPlayer rightPlayer;

    public AbstractMultiplayerScene(final SceneItem scene, final int width, final int figureSize) {
        super(scene);
        for (int i = 0; i < PLAYERS; i++)
            gamePaused[i] = new SimpleEntity(GAME_PAUSED, layer);

        if (width < figureSize)
            throw new IllegalStateException("Glass too narrow for figures");
    }

    protected <T extends AbstractMultiplayerGame> void changes(T[] games) {
        if (gameOver)
            return;

        if (checkGameOver(games)) {
            for (int i = 0; i < PLAYERS; i++)
                games[i].setGameOver();
            return;
        }
        processScene(games);
    }

    protected <T extends AbstractMultiplayerGame> void setGameOver(T[] games) {
        super.setGameOver();
        for (int i = 0; i < PLAYERS; i++)
            games[i].setGameOver();
    }

    protected <T extends AbstractMultiplayerGame> void hideGlass(T[] games) {
        for (int i = 0; i < PLAYERS; i++) {
            games[i].getGlass().pauseOn();
            gamePaused[i].spawn(new Vector2f(games[i].getX() + games[i].getForecast().getFigureSize() * BOX + 25, Y + BOX * 3 + BORDER));
        }
    }

    protected <T extends AbstractMultiplayerGame> void showGlass(T[] games) {
        for (int i = 0; i < PLAYERS; i++) {
            gamePaused[i].unspawn();
            games[i].getGlass().pauseOff();
        }
    }

    private <T extends AbstractMultiplayerGame> boolean checkGameOver(T[] games) {
        for (int i = 0; i < PLAYERS; i++)
            if (games[i].isGameOver()) {
                gameOver = true;
                losersAndWinners(games, i);
                saveOutcome(i);
                Sound.playNeutral();
            }
        return gameOver;
    }

    private <T extends AbstractMultiplayerGame> void losersAndWinners(T[] games, final int loserNumber) {
        for (int i = 0; i < PLAYERS; i++) {
            final int figureSize = games[i].getForecast().getFigureSize();
            if (i == loserNumber)
                loser.spawn(new Vector2f(games[i].getX() + figureSize * BOX + 25, Y + BOX * 3 + BORDER));
            else {
                winner.spawn(new Vector2f(games[i].getX() + figureSize * BOX + 25, Y + BOX * 3 + BORDER));
                Victories.addVictory(i);
            }
        }
    }

    private void saveOutcome(final int loserNumber) {
        if (loserNumber == 0)
            Objects.requireNonNull(DbHandler.getInstance()).saveGameOutcome(rightPlayer, leftPlayer);
        else
            Objects.requireNonNull(DbHandler.getInstance()).saveGameOutcome(leftPlayer, rightPlayer);
    }

    private static <T extends AbstractMultiplayerGame> void processScene(T[] games) {
        for (int i = 0; i < PLAYERS; i++) {
            games[i].processStage();
            getYucks(games, i);
        }
    }

    private static <T extends AbstractMultiplayerGame> void getYucks(T[] game, final int n) {
        for (int j = 0; j < PLAYERS; j++)
            if (n != j)
                game[n].addYuck(game[j].getYucksForEnemy());
    }
}
