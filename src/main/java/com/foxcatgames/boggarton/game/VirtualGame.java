package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.DOWN;
import static com.foxcatgames.boggarton.Const.LEFT;
import static com.foxcatgames.boggarton.Const.RIGHT;
import static com.foxcatgames.boggarton.Const.UP;

import com.foxcatgames.boggarton.game.figure.VirtualFigure;
import com.foxcatgames.boggarton.game.forecast.VirtualForecast;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.glass.VirtualGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class VirtualGame extends AbstractGame<VirtualBrick, VirtualFigure, VirtualGlass, VirtualForecast>
        implements IAutomatedGame<VirtualBrick, VirtualFigure, VirtualGlass, VirtualForecast> {

    private final GameAutomation<VirtualBrick, VirtualFigure, VirtualGlass, VirtualForecast> gameAutomation;

    public VirtualGame(final int width, final int height, final int prognosis, final int figureSize, final int difficulty, final RandomTypes randomType) {
        this.gameAutomation = new GameAutomation<>();
        forecast = new VirtualForecast(prognosis, figureSize, difficulty, randomType);
        glass = new VirtualGlass(width, height);
    }

    public void initLogger() {
        gameAutomation.initLogger(this);
    }

    public void closeLogger() {
        gameAutomation.closeLogger();
    }

    @Override
    protected void nextStage() {
        gameAutomation.nextStage(stage, this);
        stage = stage.getNextStage(reactionDetected);
    }

    public void processStage() {
        switch (stage) {
        case NEXT:
            needNewFigure = true;
            nextStage();
            break;
        case APPEAR:
            nextStage();
            break;
        case FALL:
            gameAutomation.executeCommand();
            fall();
            break;
        case SET:
            nextStage();
            break;
        case PROCESS:
            processGlass();
            break;
        case COMPRESS:
            compress();
            break;
        default:
        }
    }

    private void fall() {
        if (!glass.moveDown()) {
            nextStage();
        }
    }

    private void processGlass() {
        if (!glassProcessed) {
            killedBricks = glass.findChainsToKill();
            glassProcessed = true;
        }
        if (!killedBricks)
            nextStage();

        glass.killChains();
        nextStage();
    }

    @Override
    public void setGameOver() {
        gameAutomation.setGameOver(this);
    }

    @Override
    public void setSimpleGameOver(IAutomatedGame<VirtualBrick, VirtualFigure, VirtualGlass, VirtualForecast> game) {
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
    public Pair<GlassState<VirtualBrick, VirtualFigure>, VirtualForecast> getBuffer() throws InterruptedException {
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
    public void restoreSpeed() {
    }

    @Override
    public void setMaxSpeed() {
    }

    @Override
    protected void resumeScore() {
        gameAutomation.resumeScore(this);
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
