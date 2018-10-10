package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.GAME_PAUSED;
import static com.foxcatgames.boggarton.Const.LOSER;
import static com.foxcatgames.boggarton.Const.WINNER;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.game.MultiplayerGame;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.glass.MultiplayerGlass;
import com.foxcatgames.boggarton.game.utils.DbHandler;
import com.foxcatgames.boggarton.game.utils.Victories;
import com.foxcatgames.boggarton.players.IPlayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

abstract public class AbstractMultiplayerScene extends AbstractPlayingScene<Brick, SimpleFigure, MultiplayerGlass, SimpleForecast> {

    protected static final int X = 90;

    protected static final String[] PLAYERS_NAMES = { "First", "Second" };

    protected static final int PLAYERS = 2;
    private final SimpleEntity[] winners;
    private SimpleEntity loser;

    protected long pauseBetweenGames;
    protected MultiplayerGame[] game;
    private final SimpleEntity gamePaused[];

    protected IPlayer first;
    protected IPlayer second;

    AbstractMultiplayerScene(final SceneItem scene, final int width, final int height, final int[] prognosis, final int figureSize,
            YuckTypes yuckType, final RandomTypes randomType, final DifficultyTypes difficulty) {
        super(scene);
        gamePaused = new SimpleEntity[PLAYERS];
        for (int i = 0; i < PLAYERS; i++)
            gamePaused[i] = new SimpleEntity(GAME_PAUSED, layer);

        if (width < figureSize)
            throw new IllegalStateException("Glass too narrow for figures");

        winners = new SimpleEntity[PLAYERS];
        game = new MultiplayerGame[PLAYERS];

        for (int i = 0; i < PLAYERS; i++) {
            game[i] = new MultiplayerGame(layer, X + 446 * i, Y, width, height, prognosis[i], figureSize, difficulty.getSetSize(), Victories.getVictories(i),
                    yuckType, randomType, i == 0 ? Const.SOUNDS_LEFT : Const.SOUNDS_RIGHT);
            game[i].setName(PLAYERS_NAMES[i]);
        }

        for (int i = 0; i < PLAYERS; i++) {
            game[i].initLogger();
            game[i].startGame();
        }
    }

    abstract protected void checkAuto();

    private void losersAndWinners(final int loserNumber) {
        for (int i = 0; i < PLAYERS; i++) {
            final int figureSize = getFigureSize(game[i]);
            if (i == loserNumber) {
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

    protected void saveOutcome(final int loserNumber) {
        if (loserNumber == 0)
            DbHandler.getInstance().saveGameOutcome(second, first);
        else
            DbHandler.getInstance().saveGameOutcome(first, second);
    }

    @Override
    protected void changes() {
        for (int i = 0; i < PLAYERS; i++)
            if (game[i].isGameOver() && loser == null) {
                losersAndWinners(i);
                saveOutcome(i);
                pauseBetweenGames = System.currentTimeMillis();
            }

        checkAuto();

        if (loser != null)
            return;

        for (int i = 0; i < PLAYERS; i++) {
            game[i].processStage();
            getYucks(i);
        }
    }

    private void getYucks(final int n) {
        for (int j = 0; j < PLAYERS; j++)
            if (n != j)
                game[n].addYuck(game[j].getYuckForEnemy());
    }

    @Override
    protected void setGameOver() {
        super.setGameOver();
        for (int i = 0; i < PLAYERS; i++) {
            game[i].setGameOver();
            game[i].closeLogger();
        }
    }

    @Override
    protected void hideGlass() {
        for (int i = 0; i < PLAYERS; i++) {
            game[i].getGlass().pauseOn();
            gamePaused[i].spawn(new Vector2f(game[i].getX() + getFigureSize(game[i]) * BOX + 25, Y + BOX * 3 + BORDER));
        }
    }

    @Override
    protected void showGlass() {
        for (int i = 0; i < PLAYERS; i++) {
            gamePaused[i].unspawn();
            game[i].getGlass().pauseOff();
        }
    }
}
