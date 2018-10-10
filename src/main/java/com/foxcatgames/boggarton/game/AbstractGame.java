package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.game.StageItem.START;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.forecast.IForecast;
import com.foxcatgames.boggarton.game.glass.IGlass;
import com.foxcatgames.boggarton.game.glass.IGlassState;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.OuterCommand;
import com.foxcatgames.boggarton.game.utils.Pair;

/**
 * The game is a glass, a forecast and the all motion inside the Glass.
 */
abstract public class AbstractGame<B extends IBrick, F extends IFigure<B>, G extends IGlass<B, F>, P extends IForecast<B, F>> {

    protected G glass;
    protected P forecast;

    protected int yucksForEnemies;
    protected boolean glassProcessed;
    protected boolean killedBricks;
    protected String oldGlassState;

    protected boolean reactionDetected = false;
    protected boolean dropPressed = false;

    protected String name = "default";
    protected StageItem stage = START;
    protected boolean isLoggerInit = false;

    protected GameLogger gameLogger = null;

    final protected Pair<IGlassState<B, F>, P> buffer = new Pair<>(null, null);
    final protected OuterCommand command = new OuterCommand();
    protected int targetPosition = 0;

    protected boolean needNewFigure = true;
    protected int lastScore = 0;

    abstract public void processStage();

    abstract protected void nextStage();

    abstract protected void resumeScore();

    protected F nextFigure() {
        dropPressed = false;
        F figure = null;
        if (needNewFigure) {
            figure = forecast.getForecast();
            targetPosition = glass.newFigure(figure);
        }
        if (!glass.getGlassState().canTakeNewFigure(targetPosition)) {
            setGameOver();
            return null;
        }
        needNewFigure = false;
        resumeScore();
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
        synchronized (buffer) {
            glass.setGameOver();
            buffer.notify();
            executeCommand();
        }
    }

    public Pair<IGlassState<B, F>, P> getBuffer() throws InterruptedException {
        synchronized (buffer) {
            while (buffer.isEmpty() && isGameOn())
                buffer.wait();
            buffer.notify();
        }
        return buffer;
    }

    public void clearBuffer() {
        synchronized (buffer) {
            buffer.setFirst(null);
            buffer.setSecond(null);
            buffer.notify();
        }
    }

    protected void fillBuffer() {
        synchronized (buffer) {
            buffer.setFirst(glass.getGlassState());
            buffer.setSecond(forecast);
            buffer.notify();
        }
    }

    protected void executeCommand() {
        if (command.getCommand() != null)
            synchronized (command) {
                command.execute();
                command.notify();
            }
    }

    public void sendCommand(ICommand cmd) throws InterruptedException {
        synchronized (command) {
            while (command.getCommand() != null)
                command.wait();
            command.setCommand(cmd);
            command.notify();
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
        logEvent("C");
    }

    public void moveLeft() {
        glass.moveLeft();
        logEvent("L");
    }

    public void moveRight() {
        glass.moveRight();
        logEvent("R");
    }

    public void dropFigure() {
        dropPressed = true;
        logEvent("D");
    }

    public void waitNextFigure() {
        logEvent("N\n");
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
            logEvent(Const.FIGURE_STR + figure);
    }

    protected void logScore(final int diffScore) {
        logEvent(Const.SCORE_STR + diffScore + "\n");
    }

    protected void logGlass(final String glassState) {
        logEvent(glassState);
    }

    protected void logMoves() {
        logEvent(Const.MOVES_STR);
    }

    protected void logYuck(final String yuck) {
        logEvent(Const.YUCK_STR + yuck + "\n");
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
