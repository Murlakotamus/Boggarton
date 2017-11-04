package com.foxcatgames.boggarton.scenes;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Timer;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.Layer;

abstract public class AbstractScene {

    final static public Timer TIMER = new Timer(); // game timer, a part of the
                                                   // engine
    private SceneItem scene; // current scene
    protected SceneItem nextScene; // a next scene for a current scene

    protected static int difficulty = 5;
    public  static int prognosis = 3;
    public static int size = 3;

    protected final Layer layer; // the layer for draw, still only one
    private static float lastTime = TIMER.getTime(); // the last tick moment

    // How much time spent since the last tick. It is used by sprite engine
    public static float tick;
    public static float fadeAlpha;

    static public void heartBeat() {
        Timer.tick();
        tick = TIMER.getTime() - lastTime;
        lastTime = TIMER.getTime();
    }

    protected AbstractScene(final SceneItem scene) {
        this.scene = scene;
        nextScene = scene;
        layer = new Layer();
    }

    public SceneItem play() {
        start();
        run();
        terminate();

        return nextScene;
    }

    abstract protected void changes();

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

    protected void start() {
    }

    protected void nextScene(final SceneItem scene) {
        nextScene = scene;
    }

    protected void terminate() {
        EventManager.getInstance().clear();
        layer.removeAll();
    }
}