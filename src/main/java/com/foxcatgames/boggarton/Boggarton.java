package com.foxcatgames.boggarton;

import com.foxcatgames.boggarton.game.utils.DbHandler;
import com.foxcatgames.boggarton.scenes.SceneItem;

public class Boggarton {

    public static void run() {
        SceneItem scene = SceneItem.INTRO;
        while (scene != SceneItem.FINISH_GAME)
            scene = scene.createScene().play();
    }

    public static void playVirtualGame() {
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            int number = 0;
            try {
                number = Integer.parseInt(args[0]);
                for (int i = 0; i < number; i++)
                    Boggarton.playVirtualGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        Sound.init();
        Graphics.init();

        Boggarton.run();

        DbHandler.close();
        Graphics.destroy();
        Sound.destroy();
    }
}
