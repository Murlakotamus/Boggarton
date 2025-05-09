package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Timer;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.engine.Layer;

abstract public class AbstractScene {

    final static public Timer TIMER = new Timer();
    private final SceneItem scene;
    protected SceneItem nextScene;

    protected final Layer layer = new Layer();
    private static double lastTime = TIMER.getTime();

    public static double tick;

    abstract protected void changes();

    abstract protected void start();

    static public void heartBeat() {
        Timer.tick();
        tick = TIMER.getTime() - lastTime;
        lastTime = TIMER.getTime();
    }

    protected AbstractScene(final SceneItem scene) {
        this.scene = scene;
        nextScene = scene;
    }

    public SceneItem play() {
        start();
        run();
        terminate();

        return nextScene;
    }

    private void renderScene() {
        if (!TIMER.isPaused())
            changes();

        layer.update();
        layer.render();

        Display.update();
    }

    protected void run() {
        while (scene == nextScene) {
            heartBeat();
            renderScene();
            EventManager.checkEvents();
            if (Display.isCloseRequested())
                nextScene(SceneItem.FINISH_GAME);
        }
    }

    protected void nextScene(final SceneItem scene) {
        nextScene = scene;
    }

    protected void terminate() {
        EventManager.clear();
        layer.removeAll();
    }

    protected void addKeyEscape(final SceneItem sceneItem) {
        final KeyListener escape = new KeyListener() {
            @Override
            public void onKeyUp() {
                nextScene(sceneItem);
            }
        };
        EventManager.addListener(Keyboard.KEY_ESCAPE, escape);
    }

    public static float getTime() {
        return TIMER.getTime();
    }
}
