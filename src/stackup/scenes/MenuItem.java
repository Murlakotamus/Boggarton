package stackup.scenes;

public enum MenuItem {

    ONE_PLAYER_GAME("Single player game", Scene.ONE_PLAYER_GAME),
    PLAYER_VS_COMP("Player vs. Comp", Scene.PLAYER_VS_COMP),
    ONE_PLAYER_DEMO("One player demo", Scene.ONE_PLAYER_DEMO),
    TWO_PLAYERS_GAME("Player vs. player", Scene.TWO_PLAYERS_GAME),
    TWO_PLAYERS_DEMO("Two players demo", Scene.TWO_PLAYERS_DEMO),
    DIFFICULTY("Difficulty", Scene.DIFFICULTY),
    FIGURE_SIZE("Figure size", Scene.SIZE),
    ABOUT("About", Scene.ABOUT);

    final private Scene scene;
    final private String name;

    MenuItem(final String name, final Scene scene) {
        this.name = name;
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }
}
