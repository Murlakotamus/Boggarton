package stackup.scenes;

import static stackup.Const.ABOUT;
import static stackup.Const.SCREEN_WIDTH;
import static stackup.Const.TITLE;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import stackup.engine.EventManager;
import stackup.engine.KeyListener;
import stackup.entity.SimpleEntity;

public class About extends AbstractLogo {

    private final SimpleEntity title;
    private final SimpleEntity about;

    public About() {
        super(Scene.ABOUT);

        title = new SimpleEntity(TITLE, layer);
        about = new SimpleEntity(ABOUT, layer);

        title.spawn(new Vector2f(TITLE_X, TITLE_Y));
        about.spawn(new Vector2f((SCREEN_WIDTH / 2) - (about.width / 2), 240));

        final KeyListener escape = new KeyListener() {
            public void onKeyUp() {
                nextScene(Scene.MENU);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);

        final KeyListener enter = new KeyListener() {
            public void onKeyUp() {
                nextScene(Scene.MENU);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_RETURN, enter);

    }
}
