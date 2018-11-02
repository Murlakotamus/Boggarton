package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.CYCLE;
import static com.foxcatgames.boggarton.Const.FIGURE_STR;
import static com.foxcatgames.boggarton.Const.MOVES_STR;
import static com.foxcatgames.boggarton.Const.NEXT;
import static com.foxcatgames.boggarton.Const.SCORE_STR;
import static com.foxcatgames.boggarton.Const.SHIFT;
import static com.foxcatgames.boggarton.Const.YUCK_STR;

import java.util.Map;

import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractForecast;
import com.foxcatgames.boggarton.game.glass.AbstractGlass;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.OuterCommand;
import com.foxcatgames.boggarton.game.utils.Pair;

public class GameAutomation<B extends IBrick, F extends AbstractFigure<B>, G extends AbstractGlass<B, F>, P extends AbstractForecast<B, F>> {

    private final Map<String, Integer> sounds;
    protected final OuterCommand command = new OuterCommand();
    protected final Pair<GlassState<B, F>, P> gamestateBuffer = new Pair<>(null, null);

    protected boolean isLoggerInit;
    protected GameLogger gameLogger;

    protected boolean turnFinished;

    public boolean processStage(final StageItem stage) {
        switch (stage) {
        case APPEAR:
        case FALL:
            executeSoundCommand();
            break;
        case SET:
            if (!turnFinished) {
                executeCommand();
                Thread.yield();
                return false;
            }
            break;
        default:
        }
        return true;
    }

    public void nextStage(final StageItem stage, final IAutomatedGame<B, F, G, P> game) {
        if (stage == StageItem.NEXT) {
            fillBuffer(game);
            logFigure(game.getGlass().figure());
            logMoves();
            turnFinished = false;
        }
    }

    public GameAutomation(final Map<String, Integer> sounds) {
        this.sounds = sounds;
    }

    public GameAutomation() {
        this.sounds = null;
    }

    public void executeSoundCommand() {
        if (!(sounds(SHIFT) || sounds(CYCLE)))
            executeCommand();
    }

    private boolean sounds(final String soundSource) {
        return Sound.isPlaying(sounds.get(soundSource));
    }

    public void executeCommand() {
        synchronized (command) {
            if (command.getCommand() != null)
                command.execute();
            command.notify();
        }
    }

    public void sendCommand(final ICommand cmd) throws InterruptedException {
        synchronized (command) {
            while (command.getCommand() != null)
                command.wait();
            command.setCommand(cmd);
            command.notify();
        }
    }

    public void setGameOver(final IAutomatedGame<B, F, G, P> game) {
        synchronized (gamestateBuffer) {
            game.setSimpleGameOver(game);
            gamestateBuffer.notify();
            executeCommand();
            closeLogger();
        }
    }

    public Pair<GlassState<B, F>, P> getBuffer(final IAutomatedGame<B, F, G, P> game) throws InterruptedException {
        synchronized (gamestateBuffer) {
            while (gamestateBuffer.isEmpty() && game.isGameOn())
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

    protected void fillBuffer(final IAutomatedGame<B, F, G, P> game) {
        synchronized (gamestateBuffer) {
            gamestateBuffer.setFirst(game.getGlass().getGlassState());
            gamestateBuffer.setSecond(game.getForecast());
            gamestateBuffer.notify();
        }
    }

    public void finishTurn() {
        logEvent(NEXT + "\n");
        turnFinished = true;
    }

    public void initLogger(final IAutomatedGame<B, F, G, P> game) {
        gameLogger = new GameLogger(game.getName());
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

    public void logEvent(final char c) {
        logEvent("" + c);
    }

    public void logEvent(final String str) {
        if (isLoggerInit)
            gameLogger.logEvent(str);
    }

    public void resumeScore(final IAutomatedGame<B, F, G, P> game) {
        final int diff = game.getGlass().getGlassState().getScore() - game.getLastScore();
        if (diff > 0) {
            logScore(diff);
            if (diff > 100) {
                logGlass(game.getOldGlassState());
                logGlass(game.getGlass().getGlassState().toString());
            }
        }
    }
}
