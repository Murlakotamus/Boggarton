package com.foxcatgames.boggarton.game;

import com.foxcatgames.boggarton.game.figure.VirtualFigure;
import com.foxcatgames.boggarton.game.forecast.VirtualForecast;
import com.foxcatgames.boggarton.game.glass.VirtualGlass;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class VirtualGame extends AbstractGame<VirtualBrick, VirtualFigure, VirtualGlass, VirtualForecast> {

    public VirtualGame(final int width, final int height, final int prognosis, final int figureSize, final int difficulty, final RandomTypes randomType) {
        forecast = new VirtualForecast(prognosis, figureSize, difficulty, randomType);
        glass = new VirtualGlass(width, height);
    }

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
            executeCommand();
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

    protected VirtualFigure nextFigure() {
        dropPressed = false;
        VirtualFigure figure = null;
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
}
