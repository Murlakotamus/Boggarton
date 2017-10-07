package stackup.scenes;

import static stackup.Const.SCREEN_HEIGHT;
import static stackup.Const.TITLE;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import stackup.engine.EventManager;
import stackup.engine.KeyListener;
import stackup.entity.SimpleEntity;

public class Intro extends AbstractLogo {

    public Intro() {
        super(Scene.INTRO);
        y = SCREEN_HEIGHT;
        title = new SimpleEntity(TITLE, layer);
        title.spawn(new Vector2f(TITLE_X, y));

        final KeyListener escape = new KeyListener() {
            public void onKeyUp() {
                nextScene(Scene.MENU);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }

    @Override
    protected void changes() {
        moveUp();
        if (y <= TITLE_Y)
            nextScene(Scene.MENU);

    }

}
