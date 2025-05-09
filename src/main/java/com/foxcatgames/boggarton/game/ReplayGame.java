package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.LEFT;
import static com.foxcatgames.boggarton.Const.RIGHT;
import static com.foxcatgames.boggarton.Const.SCORE_STR;
import static com.foxcatgames.boggarton.Const.UP;
import static com.foxcatgames.boggarton.Const.YUCK_STR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.foxcatgames.boggarton.Logger;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.figure.PredefinedFigure;
import com.foxcatgames.boggarton.game.forecast.PredefinedForecast;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.glass.ReplayGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.Pair;

public class ReplayGame extends AbstractVisualGame<Brick, PredefinedFigure, ReplayGlass, PredefinedForecast>
        implements IAutomatedGame<Brick, PredefinedFigure, ReplayGlass, PredefinedForecast> {

    private static final float YUCK_PAUSE = 0.5f;
    private final GameAutomation<Brick, PredefinedFigure, ReplayGlass, PredefinedForecast> gameAutomation;
    private final List<String> events;
    private int eventNum;

    public ReplayGame(final Layer layer, final int x, final int y, final int width, final int height, final int figureSize, final List<String> events,
            final Map<String, Integer> sounds) {

        super(layer, x, y, sounds);
        gameAutomation = new GameAutomation<>(sounds);
        forecast = new PredefinedForecast(layer, new Vector2f(x, y), height, figureSize, events);
        this.events = events;

        final int[][] bricks = new int[width][height];
        final String filename = Objects.requireNonNull(this.getClass().getResource("/games/glass.txt")).getFile();
        try (final BufferedReader in = new BufferedReader(new FileReader(filename))) {
            int j = 0;
            String line;
            while ((line = in.readLine()) != null) {
                for (int i = 0; i < width; i++) {
                    final char c = line.charAt(i);
                    if (c >= 'A' && c <= 'J')
                        bricks[i][j] = c - '@';
                    else
                        bricks[i][j] = 0;
                }
                j++;
            }
        } catch (final IOException e) {
            Logger.printStackTrace(e);
        }

        glass = new ReplayGlass(layer, new Vector2f(x + figureSize * BOX + 20, y), width, height, bricks, sounds);
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                gameAutomation.processStage(stage); // set next turn
                final PredefinedFigure figure = nextFigure();
                if (figure == null || figure.getNumber() == 0) {
                    setGameOver();
                    break;
                }
                eventNum++;
            } else
                charge();
            break;
        case APPEAR:
        case FALL:
        case SET:
            if (gameAutomation.processStage(stage))
                super.processStage();
            break;
        case YUCK:
            resumeScore();
            executeYuck(events.get(eventNum++).substring(YUCK_STR.length()));
            break;
        case YUCK_PAUSE:
            stagePause(YUCK_PAUSE);
            break;
        default:
            super.processStage();
        }
    }

    @Override
    protected void nextStage() {
        startTime = getTime();
        previousTime = startTime;
        if (eventNum > 0 && eventNum < events.size() && events.get(eventNum).startsWith(SCORE_STR)) { // #122
            eventNum++;
            if (events.get(eventNum - 2).startsWith(YUCK_STR)) {
                stage = StageItem.PROCESS;
                return;
            }
        }
        final boolean executeYuck = eventNum < events.size() && events.get(eventNum).startsWith(YUCK_STR);
        gameAutomation.nextStage(stage, this);
        stage = stage.getNextStage(reactionDetected, executeYuck);
    }

    protected void executeYuck(final String yuckBricks) {
        glass.executeYuck(yuckBricks.trim());
        glass.respawn();
        Sound.play(sounds.get(Const.YUCK));
        nextStage();
    }

    @Override
    public void setGameOver() {
        gameAutomation.setGameOver(this);
    }

    @Override
    public void setSimpleGameOver(final IAutomatedGame<Brick, PredefinedFigure, ReplayGlass, PredefinedForecast> game) {
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
    public Pair<GlassState<Brick, PredefinedFigure>, PredefinedForecast> getBuffer() throws InterruptedException {
        return gameAutomation.getBuffer(this);
    }

    @Override
    public void clearBuffer() {
        gameAutomation.clearBuffer();
    }

    @Override
    protected void resumeScore() {
        gameAutomation.resumeScore(this);
        super.resumeScore();
    }

    @Override
    public void rotateFigure() {
        super.rotateFigure();
        gameAutomation.makeMove(UP);
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
