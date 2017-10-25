package com.foxcatgames.boggarton;

import com.foxcatgames.boggarton.scenes.SceneItem;

public class Boggarton {

    public static void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        SceneItem scene = SceneItem.INTRO;
        while (scene != SceneItem.FINISH_GAME)
            scene = scene.createScene().play();
    }

    public static void main(String[] args) {
        Graphics.init();
        Boggarton.run();
        Graphics.destroy();
    }
}
