package stackup.game;

import stackup.engine.Layer;

public class SinglePlayerGame extends AbstractGame {

    public SinglePlayerGame(final Layer layer, final int x, final int y, final int width,
            final int height, final int forecast, final int lenght, final int setSize) {
        super(layer, x, y, width, height, forecast, lenght, setSize);
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
            nextFigure();
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
        default:
        }
    }
}
