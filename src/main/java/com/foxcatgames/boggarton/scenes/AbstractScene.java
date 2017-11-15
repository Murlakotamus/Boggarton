package com.foxcatgames.boggarton.scenes;

import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Timer;

import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.engine.Layer;

abstract public class AbstractScene {

    final static public Timer TIMER = new Timer();
    private SceneItem scene;
    protected SceneItem nextScene;

    protected static int difficulty = 7;
    public static int prognosis = 3;
    public static int size = 3;

    protected final Layer layer;
    private static float lastTime = TIMER.getTime();

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
        addStartStackTrace();
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

    private void addStartStackTrace() {
        final KeyListener stackTrace = new KeyListener() {
            @Override
            public void onKeyUp() {
                startTrace();
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_T, stackTrace);
    }

    private void startTrace() {
        final Thread watcher = new Thread("watcher") {
            @Override
            public void run() {
                for (;;)
                    try {
                        final Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
                        for (final Map.Entry<Thread, StackTraceElement[]> elements : map.entrySet()) {
                            for (StackTraceElement element : elements.getValue())
                                Logger.err(elements.getKey().getName() + ": " + element);
                            Logger.err("-----------------------------------------------");
                        }
                        Logger.err("===================================================");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
            }
        };
        watcher.setDaemon(true);
        watcher.start();
    }
}
