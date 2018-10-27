package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.DOWN;
import static com.foxcatgames.boggarton.Const.FIGURE_STR;
import static com.foxcatgames.boggarton.Const.LEFT;
import static com.foxcatgames.boggarton.Const.MOVES_STR;
import static com.foxcatgames.boggarton.Const.NEXT;
import static com.foxcatgames.boggarton.Const.RIGHT;
import static com.foxcatgames.boggarton.Const.SCORE_STR;
import static com.foxcatgames.boggarton.Const.UP;
import static com.foxcatgames.boggarton.Const.YUCK_STR;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractForecast;
import com.foxcatgames.boggarton.game.glass.AbstractGlass;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.utils.Pair;

/**
 * The game is a glass, a forecast and the all motion inside the Glass.
 */
abstract public class AbstractGame<B extends IBrick, F extends AbstractFigure<B>, G extends AbstractGlass<B, F>, P extends AbstractForecast<B, F>> {

    protected G glass;
    protected P forecast;

    protected int yucksForEnemies;
    protected boolean glassProcessed;
    protected boolean killedBricks;
    protected String oldGlassState;

    protected boolean reactionDetected = false;
    protected boolean dropPressed = false;

    protected String name = "default";
    protected StageItem stage = StageItem.START;
    protected boolean isLoggerInit = false;

    protected GameLogger gameLogger = null;

    protected final Pair<GlassState<B, F>, P> gamestateBuffer = new Pair<>(null, null);

    protected int targetPosition = 0;

    protected boolean needNewFigure = true;
    protected int lastScore = 0;

    protected boolean turnFinished;

    abstract protected void nextStage();

    abstract protected void resumeScore();

    protected F nextFigure() {
        resumeScore();
        turnFinished = false;
        final F figure;
        if (needNewFigure) {
            figure = forecast.getForecast();
            targetPosition = glass.newFigure(figure);
        } else
            figure = null;

        if (!glass.getGlassState().canTakeNewFigure(targetPosition)) {
            setGameOver();
            return null;
        }
        needNewFigure = false;
        oldGlassState = glass.getGlassState().toString();
        return figure;
    }

    protected void compress() {
        if (killedBricks) {
            glass.removeHoles();
            glass.addReaction();
            if (glass.getReactions() > 1)
                yucksForEnemies++;
            killedBricks = false;
            reactionDetected = true;
        } else {
            glass.cleanReactions();
            reactionDetected = false;
        }
        glassProcessed = false;
        nextStage();
    }

    public void setGameOver() {
        glass.setGameOver();
    }

    public Pair<GlassState<B, F>, P> getBuffer() throws InterruptedException {
        synchronized (gamestateBuffer) {
            while (gamestateBuffer.isEmpty() && isGameOn())
                gamestateBuffer.wait();
            gamestateBuffer.notify();
        }
        return gamestateBuffer;
    }

    public void clearBuffer() {
        synchronized (gamestateBuffer) {
            gamestateBuffer.setFirst(null);
            gamestateBuffer.setSecond(null);
            gamestateBuffer.notify();
        }
    }

    protected void fillBuffer() {
        synchronized (gamestateBuffer) {
            gamestateBuffer.setFirst(glass.getGlassState());
            gamestateBuffer.setSecond(forecast);
            gamestateBuffer.notify();
        }
    }

    public void startGame() {
        nextStage();
    }

    public boolean isGameOver() {
        return glass.isGameOver();
    }

    public boolean isGameOn() {
        return !glass.isGameOver();
    }

    public void rotateFigure() {
        glass.rotate();
        logEvent(UP);
    }

    public void moveLeft() {
        glass.moveLeft();
        logEvent(LEFT);
    }

    public void moveRight() {
        glass.moveRight();
        logEvent(RIGHT);
    }

    public void dropFigure() {
        dropPressed = true;
        logEvent(DOWN);
    }

    public void finishTurn() {
        logEvent(NEXT + "\n");
        turnFinished = true;
    }

    public G getGlass() {
        return glass;
    }

    public P getForecast() {
        return forecast;
    }

    public boolean hasReaction() {
        return reactionDetected;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void initLogger() {
        gameLogger = new GameLogger(name);
        isLoggerInit = gameLogger.isInit();
    }

    public void closeLogger() {
        if (isLoggerInit) {
            gameLogger.close();
            isLoggerInit = false;
        }
    }

    protected void logFigure(final F figure) {
        if (figure != null)
            logEvent(FIGURE_STR + figure);
    }

    protected void logScore(final int diffScore) {
        logEvent(SCORE_STR + diffScore + "\n");
    }

    protected void logGlass(final String glassState) {
        logEvent(glassState);
    }

    protected void logMoves() {
        logEvent(MOVES_STR);
    }

    protected void logYuck(final String yuck) {
        if (yuck != null)
            logEvent(YUCK_STR + yuck + "\n");
    }

    private void logEvent(final char c) {
        logEvent("" + c);
    }

    private void logEvent(final String str) {
        if (isLoggerInit)
            gameLogger.logEvent(str);
    }

    public GameParams.Builder buildParams() {
        final GameParams.Builder builder = new GameParams.Builder();

        builder.setPrognosisDebth(getForecast().getDepth());
        builder.setFigureSize(getForecast().getFigureSize());
        builder.setScore(getGlass().getGlassState().getScore());
        builder.setSetSize(getForecast().getDifficulty());
        builder.setRandomName(getForecast().getRandomType().getName());
        builder.setCount(getGlass().getCount());

        return builder;
    }
}
