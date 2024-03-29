package com.foxcatgames.boggarton.scenes;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.entity.SimpleEntity;

public class OutroScene extends AbstractLogoScene {

    public OutroScene() {
        super(SceneItem.OUTRO);
        y = TITLE_Y;

        title = new SimpleEntity(Const.TITLE, layer);
        title.spawn(new Vector2f(TITLE_X, y));
        addKeyEscape(SceneItem.FINISH_GAME);
    }

    @Override
    protected void changes() {
        moveUp();
        if (y <= -Const.TITLE_HEIGHT)
            nextScene(SceneItem.FINISH_GAME);
        title.spawn(new Vector2f(TITLE_X, y));
    }

    @Override
    protected void start() {
    }
}
