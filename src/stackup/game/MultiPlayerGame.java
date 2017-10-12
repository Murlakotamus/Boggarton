package stackup.game;

import static stackup.Const.LIGHT_FONT;
import static stackup.Const.BOX;

import org.lwjgl.util.vector.Vector2f;

import stackup.engine.Layer;
import stackup.entity.Text;

public class MultiPlayerGame extends AbstractGame {

    protected int yucks;
    private final Text showVictoies;

    public MultiPlayerGame(final Layer layer, final int x, final int y, final int width,
            final int height, final int forecast, final int lenght, final int setSize,
            final int victories) {

        super(layer, x, y, width, height, forecast, lenght, setSize);
        showVictoies = new Text("Victories: " + victories, LIGHT_FONT, layer);
        showVictoies.spawn(new Vector2f(x + BOX * lenght + 20, y + BOX * height + 40));
    }

    @Override
    protected void nextStage() {
        startTime = getTime();
        previousTime = startTime;
        stage = stage.getNextStage(reactionDetected, yucks > 0);
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
            fall();
            executeCommand();
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
            executeYuck();
            break;
        case YUCK_PAUSE:
            stagePause(YUCK_PAUSE);
            break;
        default:
        }
    }

    protected void executeYuck() {
        ((Glass) glass).executeYuck();
        yucks--;
        ((Glass) glass).respawn();
        nextStage();
    }

    public int getYuckForEnemy() {
        final int result = enemiesYuck;
        enemiesYuck = 0;
        return result;
    }

    public void addYuck(final int yuck) {
        yucks = yucks + yuck;
    }

}
