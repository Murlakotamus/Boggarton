package com.foxcatgames.boggarton.scenes;

public enum MenuItem {

    ONE_PLAYER_GAME("Single player game", SceneItem.ONE_PLAYER_GAME),
    PLAYER_VS_COMP("Player vs. Comp", SceneItem.PLAYER_VS_COMP),
    ONE_PLAYER_DEMO("One player demo", SceneItem.ONE_PLAYER_DEMO),
    TWO_PLAYERS_GAME("Player vs. player", SceneItem.TWO_PLAYERS_GAME),
    TWO_PLAYERS_DEMO("Two players demo", SceneItem.TWO_PLAYERS_DEMO),
    YUCKS("Yucks:      ", SceneItem.YUCKS, "Random", "Hard"),
    DIFFICULTY("Difficulty", SceneItem.DIFFICULTY),
    FIGURE_SIZE("Figure size", SceneItem.SIZE),
    PROGNOSIS("Prognosis", SceneItem.PROGNOSIS),
    ABOUT("About", SceneItem.ABOUT);

    final private SceneItem scene;
    final private String name;
    final private String[] values;

    MenuItem(final String name, final SceneItem scene) {
        this.name = name;
        this.scene = scene;
        values = null;
    }

    MenuItem(final String name, final SceneItem scene, final String... values) {
        this.name = name;
        this.values = values;
        this.scene = scene;
    }

    public SceneItem getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }
}
