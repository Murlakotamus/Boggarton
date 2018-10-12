package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Timer;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.engine.Layer;

abstract public class AbstractScene {

    final static public Timer TIMER = new Timer();
    private SceneItem scene;
    protected SceneItem nextScene;

    protected final Layer layer = new Layer();
    private static float lastTime = TIMER.getTime();

    public static float tick;
    public static float fadeAlpha;

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
            EventManager.getInstance().checkEvents();
            if (Display.isCloseRequested())
                nextScene(SceneItem.FINISH_GAME);
            Thread.yield();
        }
    }

    protected void nextScene(final SceneItem scene) {
        nextScene = scene;
    }

    protected void terminate() {
        EventManager.getInstance().clear();
        layer.removeAll();
    }

    protected void addKeyEscape(final SceneItem sceneItem) {
        final KeyListener escape = new KeyListener() {
            @Override
            public void onKeyUp() {
                nextScene(sceneItem);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }
}
