package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;

public class SimpleGame extends AbstractGame {

    public SimpleGame(final Layer layer, final int x, final int y, final int width, final int height, final int forecast, final int lenght,
            final int difficulty, final int[] randomType) {
        super(layer, x, y, width, height, forecast, lenght, difficulty, randomType);
        glass = new SimpleGlass(layer, new Vector2f(x + lenght * BOX + 20, y), width, height);
    }

    @Override
    protected void nextStage() {
        startTime = getTime();
        previousTime = startTime;
        stage = stage.getNextStage(reactionDetected);
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                IFigure figure = nextFigure();
                needNewFigure = false;
                logFigure(figure);
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
        default:
        }
    }
}
