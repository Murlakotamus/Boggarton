package com.foxcatgames.boggarton.scenes;

public enum MenuItem {

    START("Start"), MODE("Mode", SceneItem.getAllSceneNames()), YUCKS("Yucks", "Random",
            "Hard"), DIFFICULTY("Difficulty"), FIGURE_SIZE("Figure size"), PROGNOSIS("Prognosis"), ABOUT("About");

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

    public void dropPosition() {
        position = 0;
    }

    public void nextPosition() {
        position++;
        if (position >= values.length)
            position = 0;
    }
}
