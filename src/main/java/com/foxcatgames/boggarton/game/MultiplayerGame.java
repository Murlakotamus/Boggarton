package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.LIGHT_FONT;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.glass.MultiplayerGlass;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class MultiplayerGame extends AbstractGame {

    private static final int MAX_YUCKS = 24; // 6 * 12 / 3 - theoretical limit

    public final YuckTypes yuckType;
    protected int yucks;
    private final Text showVictoies;
    private final Brick[] yuckBricks = new Brick[MAX_YUCKS];
    private final Vector2f yuckPosition;

    int oldYucks = 0;

    public MultiplayerGame(final Layer layer, final int x, final int y, final int width, final int height, final int forecast, final int lenght,
            final int setSize, final int victories, YuckTypes yuckType, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, width, height, forecast, lenght, setSize, randomType, sounds);
        this.yuckType = yuckType;
        glass = new MultiplayerGlass(layer, new Vector2f(x + lenght * BOX + 20, y), width, height, setSize, sounds);
        showVictoies = new Text("Victories: " + victories, LIGHT_FONT, layer);
        showVictoies.spawn(new Vector2f(x + BOX * lenght + 20, y + BOX * height + 40));
        yuckPosition = new Vector2f(x + BOX * lenght + 20 + width * BOX + 15, y + BOX * height - BOX + 5);
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

    public int getYuckForEnemy() {
        final int result = yucksForEnemies;
        yucksForEnemies = 0;
        return result;
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

    protected void executeYuck() {
        String yuck = ((MultiplayerGlass) glass).executeYuck(yuckType);
        logYuck(yuck);
        yucks--;
        ((MultiplayerGlass) glass).respawn();
        Sound.play(sounds.get(Const.YUCK));
        drawYucks();
        nextStage();
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

    public YuckTypes getYuckType() {
        return yuckType;
    }
}
