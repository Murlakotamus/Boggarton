package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.YUCK;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

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
        super(layer, x, y, width, height, forecast, lenght, 0);
        this.forecast = new PredefinedForecast(layer, new Vector2f(x, y), height, lenght, events);
        this.events = events;

        final int[][] bricks = new int[width][height];
        final String filename = this.getClass().getResource("/games/glass.txt").getFile();
        try (final BufferedReader in = new BufferedReader(new FileReader(new File(filename)))) {
            int j = 0;
            String line;
            while ((line = in.readLine()) != null) {
                for (int i = 0; i < width; i++) {
                    char c = line.charAt(i);
                    if (c >= 'A' && c <= 'J')
                        bricks[i][j] = (int) (c - 64);
                    else
                        bricks[i][j] = 0;
                }
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        glass = new ReplayGlass(layer, new Vector2f(x + lenght * BOX + 20, y), width, height, bricks);
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                IFigure figure = nextFigure();
                if (figure == null || figure.getNumber() == 0) {
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
            if (!dropPressed)
                stagePause(APPEAR_PAUSE);
            else
                nextStage();
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
