package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.HEIGHT;
import static com.foxcatgames.boggarton.Const.WIDTH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SceneItem {
    INTRO, MENU, GAME("Game"), PRACTICE("Practice"), ONE_PLAYER_DEMO("One player demo"), PLAYER_VS_COMP("Player vs. computer"), TWO_PLAYERS_GAME(
            "Two players game"), TWO_PLAYERS_DEMO("Two players demo"), REPLAY_GAME("Replay game"), ABOUT, OUTRO, FINISH_GAME;

    private static List<SceneItem> gameScenes = Arrays.asList(GAME, PRACTICE, PLAYER_VS_COMP, TWO_PLAYERS_GAME, ONE_PLAYER_DEMO, TWO_PLAYERS_DEMO, REPLAY_GAME);

    private final String sceneName;
    private static final int[] FORECASTS = { AbstractScene.prognosis, AbstractScene.prognosis };
    private static boolean yuckStrategy = false;
    private static SceneItem currentGameScene = GAME;

    SceneItem() {
        sceneName = null;
    }

    SceneItem(final String sceneName) {
        this.sceneName = sceneName;
    }

    public AbstractScene createScene() {
        switch (this) {
        case INTRO:
            return new IntroScene();
        case MENU:
            return new MenuScene();
        case GAME:
        case PRACTICE:
            return new OnePlayerGame(WIDTH, HEIGHT, AbstractScene.prognosis, AbstractScene.size);
        case ONE_PLAYER_DEMO:
            return new OnePlayerDemo(WIDTH, HEIGHT, 2, AbstractScene.size);
        case PLAYER_VS_COMP:
            return new PlayerVsCompGame(WIDTH, HEIGHT, FORECASTS, AbstractScene.size, yuckStrategy);
        case TWO_PLAYERS_GAME:
            return new PlayerVsPlayerGame(WIDTH, HEIGHT, FORECASTS, AbstractScene.size, yuckStrategy);
        case TWO_PLAYERS_DEMO:
            return new TwoPlayersDemo(WIDTH, HEIGHT, new int[] { 3, 2 }, AbstractScene.size, yuckStrategy);
        case REPLAY_GAME:
            return new Replay(WIDTH, HEIGHT, 3, AbstractScene.size);
        case OUTRO:
            return new OutroScene();
        default:
            return new AboutScene();
        }
    }

    public static int nextStartScene() {
        int startSceneNumber = gameScenes.indexOf(currentGameScene) + 1;
        if (startSceneNumber >= gameScenes.size())
            startSceneNumber = 0;
        currentGameScene = gameScenes.get(startSceneNumber);
        return gameScenes.indexOf(currentGameScene);
    }

    public static void dropStartScene() {
        currentGameScene = gameScenes.get(0);
    }

    public static SceneItem getStartScene() {
        return currentGameScene;
    }

    public static int changeYucksStrategy() {
        yuckStrategy = !yuckStrategy;
        return yuckStrategy ? 1 : 0;
    }

    public static void dropYucksStrategy() {
        yuckStrategy = false;
    }

    public static boolean getYuckStrategy() {
        return yuckStrategy;
    }

    public String getSceneName() {
        return sceneName;
    }

    public static String[] getAllSceneNames() {
        List<String> list = new ArrayList<>();
        for (SceneItem sceneItem : gameScenes)
            list.add(sceneItem.getSceneName());
        String result[] = new String[list.size()];
        return list.toArray(result);
    }
};
