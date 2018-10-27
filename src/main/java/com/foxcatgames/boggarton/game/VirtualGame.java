package com.foxcatgames.boggarton.game;

import com.foxcatgames.boggarton.game.figure.VirtualFigure;
import com.foxcatgames.boggarton.game.forecast.VirtualForecast;
import com.foxcatgames.boggarton.game.glass.VirtualGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class VirtualGame extends AbstractGame<VirtualBrick, VirtualFigure, VirtualGlass, VirtualForecast> implements IAutomatedGame {

    private final GameAutomation gameAutomation;

    public VirtualGame(final int width, final int height, final int prognosis, final int figureSize, final int difficulty, final RandomTypes randomType) {
        this.gameAutomation = new GameAutomation(null);
        forecast = new VirtualForecast(prognosis, figureSize, difficulty, randomType);
        glass = new VirtualGlass(width, height);
    }

    @Override
    protected void nextStage() {
        stage = stage.getNextStage(reactionDetected);
    }

    public void processStage() {
        switch (stage) {
        case NEXT:
            logFigure(nextFigure());
            logMoves();
            fillBuffer();
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

    @Override
    protected void resumeScore() {
        final int diff = getGlass().getGlassState().getScore() - lastScore;
        if (diff > 0) {
            logScore(diff);
            if (diff > 100) {
                logGlass(oldGlassState);
                logGlass(glass.getGlassState().toString());
            }
        }
        lastScore = getGlass().getGlassState().getScore();
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
        gameAutomation.setGameOver(this, gamestateBuffer);
    }

    @Override
    public void setSimpleGameOver(IAutomatedGame game) {
        if (game == this)
            super.setGameOver();
    }

    @Override
    public void sendCommand(final ICommand cmd) throws InterruptedException {
        gameAutomation.sendCommand(cmd);
    }
}
