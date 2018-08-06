package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public enum MenuItem {

    START("Start"), MODE("Mode", SceneItem.getAllSceneNames()), YUCKS("Yucks", YuckTypes.getAllYuckNames()), RANDOM_TYPE("Brick's appearance",
            RandomTypes.getRandomTypeNames()), DIFFICULTY("Difficulty",
                    DifficultyTypes.getAllDifficultyNames()), FIGURE_SIZE("Figure size"), PROGNOSIS("Prognosis"), SOUND("Sound", "On", "Off"), ABOUT("About");

    final private String name;
    final private String[] values;
    private int position;

    MenuItem(final String name) {
        this.name = name;
        values = null;
        position = -1;
    }

    MenuItem(final String name, final String... values) {
        this.name = name;
        this.values = values;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }

}
