package stackup.scenes;

public enum MenuItem {

    ONE_PLAYER_GAME("Single player game", Scene.ONE_PLAYER_GAME),
    PLAYER_VS_COMP("Player vs. Comp", Scene.PLAYER_VS_COMP),
    ONE_PLAYER_DEMO("One player demo", Scene.ONE_PLAYER_DEMO),
    TWO_PLAYERS_GAME("Player vs. player", Scene.TWO_PLAYERS_GAME),
    TWO_PLAYERS_DEMO("Two players demo", Scene.TWO_PLAYERS_DEMO),
    YUCKS("Yucks:      ", Scene.YUCKS, "Random", "Hard"),
    DIFFICULTY("Difficulty", Scene.DIFFICULTY),
    FIGURE_SIZE("Figure size", Scene.SIZE),
    PROGNOSIS("Prognosis", Scene.PROGNOSIS),
    ABOUT("About", Scene.ABOUT);

    final private Scene scene;
    final private String name;
    final private String[] values;

    MenuItem(final String name, final Scene scene) {
        this.name = name;
        this.scene = scene;
        values = null;
    }

    MenuItem(final String name, final Scene scene, final String... values) {
        this.name = name;
        this.values = values;
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }
}
