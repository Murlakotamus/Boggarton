package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.DOWN;
import static com.foxcatgames.boggarton.Const.LEFT;
import static com.foxcatgames.boggarton.Const.RIGHT;
import static com.foxcatgames.boggarton.Const.UP;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class AutomatedSimpleGame extends AbstractOnePlayerGame implements IAutomatedGame<Brick, SimpleFigure, SimpleGlass, SimpleForecast> {

    private final GameAutomation<Brick, SimpleFigure, SimpleGlass, SimpleForecast> gameAutomation;

    public AutomatedSimpleGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, prognosis, figureSize, setSize, randomType, sounds);
        glass = new SimpleGlass(layer, new Vector2f(x + figureSize * BOX + 20, y), width, height, sounds);
        gameAutomation = new GameAutomation<>(sounds);
    }

    public void initLogger() {
        gameAutomation.initLogger(this);
    }

    public void closeLogger() {
        gameAutomation.closeLogger();
    }

    @Override
    public void processStage() {
        if (gameAutomation.processStage(stage))
            super.processStage();
    }

    @Override
    public void setGameOver() {
        gameAutomation.setGameOver(this);
    }

    @Override
    public void setSimpleGameOver(final IAutomatedGame<Brick, SimpleFigure, SimpleGlass, SimpleForecast> game) {
        if (game == this)
            super.setGameOver();
    }

    @Override
    public void sendCommand(final ICommand cmd) throws InterruptedException {
        gameAutomation.sendCommand(cmd);
    }

    @Override
    public boolean isYuckHappened() {
        return false;
    }

    @Override
    public void dropYuckHappened() {
    }

    @Override
    public Pair<GlassState<Brick, SimpleFigure>, SimpleForecast> getBuffer() throws InterruptedException {
        return gameAutomation.getBuffer(this);
    }

    @Override
    public void clearBuffer() throws InterruptedException {
        gameAutomation.clearBuffer();

    }

    @Override
    public void fillBuffer() {
        gameAutomation.fillBuffer(this);
    }

    @Override
    public void nextStage() {
        gameAutomation.nextStage(stage, this);
        super.nextStage();
    }

    @Override
    protected void resumeScore() {
        gameAutomation.resumeScore(this);
        super.resumeScore();
    }

    @Override
    public boolean rotateFigure() {
        final boolean result = super.rotateFigure();
        if (result)
            gameAutomation.makeMove(UP);
        return result;
    }

    @Override
    public boolean moveLeft() {
        final boolean result = super.moveLeft();
        if (result)
            gameAutomation.makeMove(LEFT);
        return result;
    }

    @Override
    public boolean moveRight() {
        final boolean result = super.moveRight();
        if (result)
            gameAutomation.makeMove(RIGHT);
        return result;
    }

    @Override
    public void dropFigure() {
        gameAutomation.makeMove(DOWN);
        super.dropFigure();
    }

    @Override
    public void finishTurn() {
        gameAutomation.finishTurn();
    }

    @Override
    public void waitChanges() throws InterruptedException {
        gameAutomation.waitChanges(this);
    }

    @Override
    public void changeHappens() {
        gameAutomation.setChanges();
    }
}
