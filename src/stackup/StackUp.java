package stackup;

import stackup.scenes.Scene;

public class StackUp {

    public static void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Scene scene = Scene.INTRO;
        while (scene != Scene.FINISH_GAME)
            scene = scene.createScene().play();
    }

    public static void main(String[] args) {
        Graphics.init();
        StackUp.run();
        Graphics.destroy();
    }
}
