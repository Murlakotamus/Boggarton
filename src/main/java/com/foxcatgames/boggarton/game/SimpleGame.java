package com.foxcatgames.boggarton.game;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class SimpleGame extends AbstractOnePlayerGame {

    public SimpleGame(final Layer layer, final int x, final int y, final int width, final int height, final int forecast, final int lenght,
            final int difficulty, final RandomTypes randomType, final int... sounds) {

        super(layer, x, y, width, height, forecast, lenght, difficulty, randomType);
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure)
                logFigure(nextFigure());
            else
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
