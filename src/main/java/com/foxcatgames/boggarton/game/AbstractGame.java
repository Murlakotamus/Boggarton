package com.foxcatgames.boggarton.game;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractForecast;
import com.foxcatgames.boggarton.game.glass.AbstractGlass;
import com.foxcatgames.boggarton.scenes.AbstractScene;

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

    protected boolean reactionDetected;
    protected boolean dropPressed;

    protected String name = "default";
    protected StageItem stage = StageItem.START;

    protected int targetPosition;
    protected int lastScore;
    protected boolean needNewFigure = true;

    abstract protected void nextStage();

    abstract protected void resumeScore();

    protected F nextFigure() {
        resumeScore();
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
        glass.setChanges(true);
    }

    public void moveLeft() {
        glass.moveLeft();
    }

    public void moveRight() {
        glass.moveRight();
    }

    public void dropFigure() {
        dropPressed = true;
    }

    public void finishTurn() {
        glass.setChanges(true);
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

    protected static float getTime() {
        return AbstractScene.getTime();
    }

    public int getLastScore() {
        return lastScore;
    }

    public String getOldGlassState() {
        return oldGlassState;
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
