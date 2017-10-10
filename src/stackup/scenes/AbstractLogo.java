package stackup.scenes;

import org.lwjgl.util.vector.Vector2f;

import stackup.Const;
import stackup.entity.SimpleEntity;

public abstract class AbstractLogo extends AbstractScene {

    static public final int TICK = 1000 / 60;

    static protected final int TITLE_X = Const.SCREEN_WIDTH / 2 - 280 / 2;
    static protected final int TITLE_Y = 100;

    protected int y;
    protected SimpleEntity title;

    AbstractLogo(final Scene scene) {
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
