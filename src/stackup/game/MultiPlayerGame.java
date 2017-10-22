package stackup.game;

import static stackup.Const.BOX;
import static stackup.Const.LIGHT_FONT;

import org.lwjgl.util.vector.Vector2f;

import stackup.Const;
import stackup.engine.Layer;
import stackup.entity.Brick;
import stackup.entity.Text;

public class MultiplayerGame extends AbstractGame {

    public static final int MAX_YUCKS = 20;
    public final boolean yuckStrategy;
    protected int yucks;
    private final Text showVictoies;
    private IGlass enemyGlass;
    private final Brick[] yuckBricks = new Brick[MAX_YUCKS];
    private final Vector2f yuckPosition;

    public MultiplayerGame(final Layer layer, final int x, final int y, final int width,
            final int height, final int forecast, final int lenght, final int difficulty,
            final int victories, boolean yuckStrategy) {

        super(layer, x, y, width, height, forecast, lenght, difficulty);
        this.yuckStrategy = yuckStrategy;
        glass = new MultiplayerGlass(layer, new Vector2f(x + lenght * BOX + 20, y), width, height, difficulty);
        showVictoies = new Text("Victories: " + victories, LIGHT_FONT, layer);
        showVictoies.spawn(new Vector2f(x + BOX * lenght + 20, y + BOX * height + 40));
        yuckPosition = new Vector2f(x + BOX * lenght + 20 + width * BOX + 15, y + BOX * height - BOX + 5);
        int num = 0;
        for (int i = 0; i < MAX_YUCKS; i++) {
            num = i;
            if (i >= difficulty)
                num = i % difficulty;
            num = num + Const.CURRENT_SET * 10 + 1;
            yuckBricks[i] = new Brick(num, layer);
        }
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                nextFigure();
                needNewFigure = false;
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
            executeYuck();
            break;
        case YUCK_PAUSE:
            stagePause(YUCK_PAUSE);
            break;
        default:
        }
    }

    public int getYuckForEnemy() {
        final int result = yucksForEnemies;
        yucksForEnemies = 0;
        return result;
    }

    public void addYuck(final int yuck) {
        yucks += yuck;
        drawYucks();
    }

    public void setEnemyGlass(IGlass glass) {
        this.enemyGlass = glass;
    }

    @Override
    protected void nextStage() {
        startTime = getTime();
        previousTime = startTime;
        stage = stage.getNextStage(reactionDetected, yucks > 0);
    }

    protected void executeYuck() {
        ((MultiplayerGlass) glass).executeYuck(yuckStrategy);
        yucks--;
        ((MultiplayerGlass) glass).respawn();
        drawYucks();
        nextStage();
    }

    private void drawYucks() {
        int yucks = getYucks();
        for (int i = MAX_YUCKS - 1; i >= yucks; i--)
            yuckBricks[i].unspawn();

        for (int i = yucks - 1; i >= 0; i--)
            yuckBricks[i].spawn(new Vector2f(yuckPosition.x, yuckPosition.y - (i * BOX)));
    }

    private int getYucks() {
        int result = enemyGlass.getGlassState().getReactionLenght() - 2;
        result = result > 0 ? result : 0;
        return result + yucks;
    }
}
