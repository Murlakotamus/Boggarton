package com.foxcatgames.boggarton.scenes;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.entity.SimpleEntity;

abstract public class AbstractLogoScene extends AbstractScene {

    static protected final int TITLE_X = Const.SCREEN_WIDTH / 2 - 560 / 2; // @FIXME 560 - ???
    static protected final int TITLE_Y = 115;

    protected int y;
    protected SimpleEntity title;

    private final float startTime = TIMER.getTime();
    private float previousTime = startTime;

    private static final int TITLE_SPEED = 600000;

    AbstractLogoScene(final SceneItem scene) {
        super(scene);
    }

    protected void moveUp() {
        final Vector2f position = title.position;
        final float currentTime = TIMER.getTime();
        final float spentTime = (currentTime - previousTime) / 1000f;
        final int currY = Math.round(position.getY());
        y = currY - Math.round(spentTime * TITLE_SPEED);
        previousTime = currentTime;
    }

    @Override
    protected void changes() {
    }
}
