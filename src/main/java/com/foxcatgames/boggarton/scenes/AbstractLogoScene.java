package com.foxcatgames.boggarton.scenes;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.entity.SimpleEntity;

abstract public class AbstractLogoScene extends AbstractScene {

    static public final int TICK = 1000 / 60;

    //static protected final int TITLE_X = Const.SCREEN_WIDTH / 2 - 560 / 2 - 115;
    static protected final int TITLE_X = Const.SCREEN_WIDTH / 2 - 560 / 2;
    static protected final int TITLE_Y = 115;

    protected int y;
    protected SimpleEntity title;

    AbstractLogoScene(final SceneItem scene) {
        super(scene);
    }

    protected void moveUp() {
        y = y - 1;
        title.spawn(new Vector2f(TITLE_X, y));
    }

    @Override
    protected void changes() {
        // Do nothing
    }
}
