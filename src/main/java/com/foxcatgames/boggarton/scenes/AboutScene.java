package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.ABOUT;
import static com.foxcatgames.boggarton.Const.SCREEN_WIDTH;
import static com.foxcatgames.boggarton.Const.TITLE;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.SimpleEntity;

public class AboutScene extends AbstractLogoScene {

    public AboutScene() {
        super(SceneItem.ABOUT);

        title = new SimpleEntity(TITLE, layer);
        SimpleEntity about = new SimpleEntity(ABOUT, layer);

        title.spawn(new Vector2f(TITLE_X, TITLE_Y));
        about.spawn(new Vector2f(((float) SCREEN_WIDTH / 2) - (about.width / 2), 300));
        addKeyEscape(SceneItem.MENU);
        addKeyEnter();
    }

    protected void addKeyEnter() {
        final KeyListener enter = new KeyListener() {
            @Override
            public void onKeyUp() {
                nextScene(SceneItem.MENU);
            }
        };
        EventManager.addListener(Keyboard.KEY_RETURN, enter);
    }
    @Override
    protected void start() {
    }

}
