package stackup.scenes;

import static stackup.Const.TITLE;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import stackup.engine.EventManager;
import stackup.engine.KeyListener;
import stackup.entity.SimpleEntity;

public class Outro extends AbstractLogo {

    public Outro() {
        super(Scene.OUTRO);
        y = TITLE_Y;

        title = new SimpleEntity(TITLE, layer);
        title.spawn(new Vector2f(TITLE_X, y));

        final KeyListener escape = new KeyListener() {
            public void onKeyUp() {
                nextScene(Scene.FINISH_GAME);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }

    @Override
    protected void changes() {
        pause();

        if (y <= -TITLE_Y)
            nextScene(Scene.FINISH_GAME);
    }

}
