package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.game.StageItem.START;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.forecast.IForecast;
import com.foxcatgames.boggarton.game.glass.IGlass;
import com.foxcatgames.boggarton.scenes.AbstractScene;

abstract public class AbstractGameState {

    protected int x, y;
    protected IGlass glass;
    protected IForecast forecast;

    protected static final float APPEAR_PAUSE = 0.3f;
    protected static final float DISAPPEAR_PAUSE = 1f;
    protected static final float SET_PAUSE = 0.1f;
    protected static final float YUCK_PAUSE = 0.5f;

    protected static final int DROPPING_SPEED = 300000;
    protected static final int CRASH_SPEED = 150000;
    protected static final int MOVING_SPEED = 5000;
    protected static final int CHARGE_SPEED = 300000;
    volatile protected int currentSpeed = MOVING_SPEED;

    protected float startTime = getTime();
    protected float previousTime = startTime;

    protected int yucksForEnemies;
    protected boolean glassProcessed;
    protected boolean reactionDetected = false;
    protected boolean killedBricks;

    protected String name = "default";
    protected StageItem stage = START;
    private boolean isLoggerInit = false;

    private GameLogger gameLogger = null;

    float getTime() {
        return AbstractScene.TIMER.getTime();
    }

    public void restoreSpeed() {
        currentSpeed = MOVING_SPEED;
    }

    public void setMaxSpeed() {
        currentSpeed = DROPPING_SPEED;
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
        logEvent("D");
    }

    public void waitNextFigure() {
        logEvent("N\n");
    }

    public IGlass getGlass() {
        return glass;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IForecast getForecast() {
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

    protected void logFigure(final IFigure figure) {
        if (figure != null)
            logEvent(Const.FIGURE + figure);
    }

    protected void logYuck(final String yuck) {
        logEvent(Const.YUCK + yuck + "\n");
    }

    private void logEvent(final String str) {
        if (isLoggerInit)
            gameLogger.logEvent(str);
    }
}
