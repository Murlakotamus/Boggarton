package stackup.scenes;

import static stackup.Const.DEFAULT_Z;
import static stackup.Const.SCREEN_HEIGHT;
import static stackup.Const.SCREEN_WIDTH;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Timer;

import stackup.engine.EventManager;
import stackup.engine.Layer;

public abstract class AbstractScene {

    final static public Timer TIMER = new Timer(); // game timer, a part of the
                                                   // engine
    private Scene scene; // current scene
    protected Scene nextScene; // a next scene for a current scene
    protected static int difficulty = 5;
    protected static int deepness = 3;

    protected final Layer layer; // the layer for draw, still only one
    private static float lastTime = TIMER.getTime(); // the last tick moment

    public static int length = 3;
    // How much time spent since the last tick. It is used by sprite engine
    public static float tick;
    public static float fadeAlpha;

    static public void heartBeat() {
        Timer.tick();
        tick = TIMER.getTime() - lastTime;
        lastTime = TIMER.getTime();
    }

    protected AbstractScene(final Scene scene) {
        this.scene = scene;
        nextScene = scene;
        layer = new Layer();
    }

    abstract protected void changes();

    protected void run() {
        while (scene == nextScene) {
            heartBeat();
            renderScene();
            EventManager.getInstance().checkEvents();
            if (Display.isCloseRequested())
                nextScene(Scene.FINISH_GAME);
            Thread.yield();
        }
    }

    private void renderScene() {
        if (!TIMER.isPaused())
            changes();

        layer.update();
        layer.render();

        Display.update();
    }

    protected void start() {
    }

    protected void nextScene(final Scene scene) {
        nextScene = scene;
    }

    protected void terminate() {
        EventManager.getInstance().clear();
        layer.removeAll();
    }

    public Scene play() {
        start();
        run();
        terminate();

        return nextScene;
    }

    void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        fadeAlpha = 0.65f;
        fadeScreen();
        fadeAlpha = 0;

        layer.render();
        fadeScreen();
    }

    public void fadeScreen() {
        if (fadeAlpha > 0.1) {

            GL11.glLoadIdentity();
            GL11.glTranslatef(0, 0, DEFAULT_Z);
            GL11.glColor4f(0, 0, 0, fadeAlpha / 1.2f);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(SCREEN_WIDTH / 2, -SCREEN_HEIGHT / 2);
            GL11.glVertex2f(-SCREEN_WIDTH / 2, -SCREEN_HEIGHT / 2);
            GL11.glVertex2f(-SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
            GL11.glVertex2f(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
            GL11.glEnd();

            GL11.glColor4f(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        }
    }
}
