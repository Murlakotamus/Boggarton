package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.LIGHT_FONT;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.glass.MultiplayerGlass;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

abstract public class AbstractMultiplayerGame extends AbstractVisualGame<Brick, SimpleFigure, MultiplayerGlass, SimpleForecast> {

    private static final int MAX_YUCKS = 24; // 6 * 12 / 3 - theoretical limit
    private static final String YUCKS_TOTAL = "Yucks: ";
    private static final float YUCK_PAUSE = 0.5f;

    public final YuckTypes yuckType;
    protected int yucks;
    protected int yucksTotal;
    private final Text showYucks;
    private final Text showVictoies;

    private final Brick[] yuckBricks = new Brick[MAX_YUCKS];
    private final Vector2f yuckPosition;

    private int oldYucks;
    private int xYuck, yYuck;

    public AbstractMultiplayerGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final int victories, YuckTypes yuckType, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, sounds);
        this.yuckType = yuckType;
        forecast = new SimpleForecast(layer, new Vector2f(x, y), prognosis, figureSize, setSize, randomType);

        xYuck = x + BOX * figureSize + 20;
        yYuck = y + BOX * height + 40;
        showYucks = new Text(YUCKS_TOTAL + yucksTotal, LIGHT_FONT, layer);
        showYucks.spawn(new Vector2f(xYuck, yYuck));

        showVictoies = new Text("Victories: " + victories, LIGHT_FONT, layer);
        showVictoies.spawn(new Vector2f(x + BOX * figureSize + 20, y + BOX * height + 65));
        yuckPosition = new Vector2f(x + BOX * figureSize + 20 + width * BOX + 15, y + BOX * height - BOX + 5);
        int num = 0;
        for (int i = 0; i < MAX_YUCKS; i++) {
            num = i;
            if (i >= setSize)
                num = i % setSize;
            num = num + Const.CURRENT_SET * 10 + 1;
            yuckBricks[i] = new Brick(num, layer);
        }
    }

    @Override
    public void processStage() {
        switch (stage) {
        case YUCK:
            resumeScore();
            executeYuck();
            break;
        case YUCK_PAUSE:
            stagePause(YUCK_PAUSE);
            break;
        default:
            super.processStage();
        }
    }

    public int getYucksForEnemy() {
        try {
            return yucksForEnemies;
        } finally {
            yucksForEnemies = 0;
        }
    }

    public void addYuck(final int yuck) {
        if (yuck == 0 || yuckType == YuckTypes.NONE)
            return;

        yucks += yuck;
        drawYucks();
    }

    @Override
    protected void nextStage() {
        startTime = getTime();
        previousTime = startTime;
        stage = stage.getNextStage(reactionDetected, yucks > 0);
    }

    protected String executeYuck() {
        final String yuck = glass.executeYuck(yuckType);
        yucksTotal++;
        yucks--;
        glass.respawn();
        Sound.play(sounds.get(Const.YUCK));
        drawYucks();
        showYucks.setString(YUCKS_TOTAL + yucksTotal);
        showYucks.spawn(new Vector2f(xYuck, yYuck));
        nextStage();
        return yuck;
    }

    private void drawYucks() {
        for (int i = MAX_YUCKS - 1; i >= yucks; i--)
            yuckBricks[i].unspawn();

        for (int i = yucks - 1; i >= 0; i--)
            yuckBricks[i].spawn(new Vector2f(yuckPosition.x, yuckPosition.y - (i * BOX)));

        if (oldYucks < yucks)
            Sound.play(sounds.get(Const.ADDYUCK));
        oldYucks = yucks;
    }

    @Override
    public GameParams.Builder buildParams() {
        final GameParams.Builder builder = super.buildParams();
        builder.setYuckName(yuckType.getName());

        return builder;
    }
}
