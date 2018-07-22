package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.SCREEN_HEIGHT;
import static com.foxcatgames.boggarton.Const.TITLE;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.entity.SimpleEntity;

public class IntroScene extends AbstractLogoScene {

    public IntroScene() {
        super(SceneItem.INTRO);
        y = SCREEN_HEIGHT;
        title = new SimpleEntity(TITLE, layer);
        title.spawn(new Vector2f(TITLE_X, y));

        final KeyListener escape = new KeyListener() {
            public void onKeyUp() {
                nextScene(SceneItem.MENU);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }

    @Override
    protected void changes() {
        moveUp();
        if (y <= TITLE_Y)
            nextScene(SceneItem.MENU);

    }

    @Override
    protected void start() {
    }
}
