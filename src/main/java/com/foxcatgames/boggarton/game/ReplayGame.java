package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.YUCK;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.forecast.PredefinedForecast;
import com.foxcatgames.boggarton.game.glass.ReplayGlass;

public class ReplayGame extends AbstractGame {

    public static final int MAX_YUCKS = 24; // 6 * 12 / 3 - theoretical limit
    protected int yucks = 0;
    final List<String> events;
    int eventNum = 0;

    public ReplayGame(final Layer layer, final int x, final int y, final int width, final int height, final int forecast, final int lenght,
            final List<String> events) {
        super(layer, x, y, width, height, 0, lenght, Const.MIN_DIFFICULTY);
        this.forecast = new PredefinedForecast(layer, new Vector2f(x, y), height, lenght, events);
        this.events = events;
        glass = new ReplayGlass(layer, new Vector2f(x + lenght * BOX + 20, y), width, height);
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                IFigure figure = nextFigure();
                if (figure == null) {
                    setGameOver();
                    break;
                }
                needNewFigure = false;
                eventNum++;
            } else
                charge();
            break;
        case APPEAR:
            executeCommand();
            stagePause(APPEAR_PAUSE);
            break;
        case FALL:
            executeCommand();
            fall();
            break;
        case SET:
            stagePause(SET_PAUSE);
            break;
        case CRASH:
            crashDown();
            break;
        case PROCESS:
            processGlass();
            break;
        case COMPRESS:
            compress();
            break;
        case YUCK:
            executeYuck(events.get(eventNum++).substring(YUCK.length()));
            break;
        case YUCK_PAUSE:
            stagePause(YUCK_PAUSE);
            break;
        default:
        }
    }

    @Override
    protected void nextStage() {
        startTime = getTime();
        previousTime = startTime;
        boolean executeYuck = eventNum < events.size() && events.get(eventNum).startsWith(YUCK);
        stage = stage.getNextStage(reactionDetected, executeYuck);
    }

    protected void executeYuck(final String yuckBricks) {
        ((ReplayGlass) glass).executeYuck(yuckBricks);
        ((ReplayGlass) glass).respawn();
        nextStage();
    }
}
