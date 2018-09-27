package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public enum MenuItem {

    START("Start"),
    MODE("Mode", SceneItem.getAllSceneNames()),
    RANDOM_TYPE("Brick's appearance", RandomTypes.getRandomTypeNames()),
    DIFFICULTY("Difficulty", DifficultyTypes.getAllDifficultyNames()),
    YUCKS("Yucks", YuckTypes.getAllYuckNames()),
    FIGURE_SIZE("Figure size"),
    PROGNOSIS("Prognosis"),
    SOUND("Sound", "On", "Off"),
    ABOUT("About");

    final private String name;
    final private String[] values;
    private int submenuElementPosition;

    MenuItem(final String name) {
        this.name = name;
        values = null;
        submenuElementPosition = -1;
    }

    MenuItem(final String name, final String... values) {
        this.name = name;
        this.values = values;
        this.submenuElementPosition = 0;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }

    public int getSubmenuElementPosition() {
        return submenuElementPosition;
    }

    public void setSubmenuElementPosition(final int position) {
        this.submenuElementPosition = position;
    }

}
