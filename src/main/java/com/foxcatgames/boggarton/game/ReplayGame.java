package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.YUCK_STR;
import static com.foxcatgames.boggarton.Const.SCORE_STR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.figure.PredefinedFigure;
import com.foxcatgames.boggarton.game.forecast.PredefinedForecast;
import com.foxcatgames.boggarton.game.glass.ReplayGlass;

public class ReplayGame extends AbstractVisualGame<Brick, PredefinedFigure, ReplayGlass, PredefinedForecast> {

    protected int yucks = 0;
    final List<String> events;
    int eventNum = 0;

    public ReplayGame(final Layer layer, final int x, final int y, final int width, final int height, final int figureSize, final List<String> events,
            final Map<String, Integer> sounds) {
        super(layer, x, y, width, height, sounds);

        this.forecast = new PredefinedForecast(layer, new Vector2f(x, y), height, figureSize, events);
        this.events = events;

        final int[][] bricks = new int[width][height];
        final String filename = this.getClass().getResource("/games/glass.txt").getFile();
        try (final BufferedReader in = new BufferedReader(new FileReader(new File(filename)))) {
            int j = 0;
            String line;
            while ((line = in.readLine()) != null) {
                for (int i = 0; i < width; i++) {
                    final char c = line.charAt(i);
                    if (c >= 'A' && c <= 'J')
                        bricks[i][j] = (int) (c - '@');
                    else
                        bricks[i][j] = 0;
                }
                j++;
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        glass = new ReplayGlass(layer, new Vector2f(x + figureSize * BOX + 20, y), width, height, bricks, sounds);
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                final PredefinedFigure figure = nextFigure();
                if (figure == null || figure.getNumber() == 0) {
                    setGameOver();
                    break;
                }
                eventNum++;
            } else
                charge();
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
        if (eventNum < events.size() && events.get(eventNum).startsWith(SCORE_STR)) { // #122
            eventNum++;
            if (events.get(eventNum - 2).startsWith(YUCK_STR)) {
                stage = StageItem.PROCESS;
                return;
            }
        }
        final boolean executeYuck = eventNum < events.size() && events.get(eventNum).startsWith(YUCK_STR);
        stage = stage.getNextStage(reactionDetected, executeYuck);
    }

    protected void executeYuck(final String yuckBricks) {
        glass.executeYuck(yuckBricks.trim());
        glass.respawn();
        Sound.play(sounds.get(Const.YUCK));
        nextStage();
    }
}
