package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.ABOUT;
import static com.foxcatgames.boggarton.Const.SCREEN_WIDTH;
import static com.foxcatgames.boggarton.Const.TITLE;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.entity.SimpleEntity;

public class AboutScene extends AbstractLogoScene {

    private final SimpleEntity title;
    private final SimpleEntity about;

    public AboutScene() {
        super(SceneItem.ABOUT);

        title = new SimpleEntity(TITLE, layer);
        about = new SimpleEntity(ABOUT, layer);

        title.spawn(new Vector2f(TITLE_X, TITLE_Y));
        about.spawn(new Vector2f((SCREEN_WIDTH / 2) - (about.width / 2), 300));

        final KeyListener escape = new KeyListener() {
            public void onKeyUp() {
                nextScene(SceneItem.MENU);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);

        final KeyListener enter = new KeyListener() {
            public void onKeyUp() {
                nextScene(SceneItem.MENU);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_RETURN, enter);
    }

    @Override
    protected void start() {
    }
}
