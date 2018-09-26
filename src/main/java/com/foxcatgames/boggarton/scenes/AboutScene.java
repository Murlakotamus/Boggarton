package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.ABOUT;
import static com.foxcatgames.boggarton.Const.SCREEN_WIDTH;
import static com.foxcatgames.boggarton.Const.TITLE;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.SimpleEntity;

public class AboutScene extends AbstractLogoScene {

    private final SimpleEntity about;

    public AboutScene() {
        super(SceneItem.ABOUT);

        title = new SimpleEntity(TITLE, layer);
        about = new SimpleEntity(ABOUT, layer);

        title.spawn(new Vector2f(TITLE_X, TITLE_Y));
        about.spawn(new Vector2f((SCREEN_WIDTH / 2) - (about.width / 2), 300));
        addKeyEscape(SceneItem.MENU);
    }

    @Override
    protected void start() {
    }
}
