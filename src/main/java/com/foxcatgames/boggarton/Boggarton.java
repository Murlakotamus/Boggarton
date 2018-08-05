package com.foxcatgames.boggarton;

import java.sql.SQLException;

import com.foxcatgames.boggarton.game.utils.DbHandler;
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

        try {
            DbHandler.getInstance().closeHandler();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Graphics.destroy();
    }
}
