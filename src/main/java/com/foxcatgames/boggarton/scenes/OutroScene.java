package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.entity.SimpleEntity;

public class OutroScene extends AbstractLogoScene {

    public OutroScene() {
        super(SceneItem.OUTRO);
        y = TITLE_Y;

        title = new SimpleEntity(Const.TITLE, layer);
        title.spawn(new Vector2f(TITLE_X, y));

        final KeyListener escape = new KeyListener() {
            public void onKeyUp() {
                nextScene(SceneItem.FINISH_GAME);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }

    @Override
    protected void changes() {
        moveUp();
        if (y <= -Const.TITLE_HEIGHT)
            nextScene(SceneItem.FINISH_GAME);
    }

}
