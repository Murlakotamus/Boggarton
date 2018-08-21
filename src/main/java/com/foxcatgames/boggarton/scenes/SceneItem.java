package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.HEIGHT;
import static com.foxcatgames.boggarton.Const.WIDTH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.SoundTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public enum SceneItem {
    INTRO, MENU, GAME("Game"), PRACTICE("Practice"), DEMO("Demo"), COMPETITION_PRACTICE("Practice with computer"), COMPETITION("Competition"), COMPETITION_DEMO(
            "Competition demo"), REPLAY("Replay game"), ABOUT, OUTRO, FINISH_GAME;

    private static List<SceneItem> gameScenes = Arrays.asList(GAME, PRACTICE, COMPETITION, COMPETITION_PRACTICE, DEMO, COMPETITION_DEMO, REPLAY);

    private final String sceneName;
    private static final int[] PROGNOSIS = { AbstractScene.prognosis, AbstractScene.prognosis };
    private static YuckTypes yuckType = YuckTypes.RANDOM;
    private static RandomTypes randomType = RandomTypes.RANDOM;
    private static DifficultyTypes difficultyType = DifficultyTypes.EASY;
    private static SoundTypes soundType = SoundTypes.ON;
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
            return new Game(WIDTH, HEIGHT, AbstractScene.prognosis, AbstractScene.figureSize, randomType, difficultyType);
        case PRACTICE:
            return new Practice(WIDTH, HEIGHT, AbstractScene.prognosis, AbstractScene.figureSize, randomType, difficultyType);
        case DEMO:
            return new Demo(WIDTH, HEIGHT, 2, AbstractScene.figureSize, randomType, difficultyType);
        case COMPETITION_PRACTICE:
            return new CompetitionPractice(WIDTH, HEIGHT, PROGNOSIS, AbstractScene.figureSize, yuckType, randomType, difficultyType);
        case COMPETITION:
            return new CompetitionGame(WIDTH, HEIGHT, PROGNOSIS, AbstractScene.figureSize, yuckType, randomType, difficultyType);
        case COMPETITION_DEMO:
            return new CompetitionDemo(WIDTH, HEIGHT, new int[] { 3, 2 }, AbstractScene.figureSize, yuckType, randomType, difficultyType);
        case REPLAY:
            return new Replay(WIDTH, HEIGHT, 3, AbstractScene.figureSize);
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

    public static int nextYucksType() {
        yuckType = yuckType.next();
        return yuckType.ordinal();
    }

    public static void dropYucksType() {
        yuckType = YuckTypes.NONE;
    }

    public static int nextRandomType() {
        randomType = randomType.next();
        return randomType.ordinal();
    }

    public static void dropRandomType() {
        randomType = RandomTypes.RANDOM;
    }

    public static int getSetSize() {
        return difficultyType.getSetSize();
    }

    public static int nextDifficultyType() {
        difficultyType = difficultyType.next();
        return difficultyType.ordinal();
    }

    public static void dropDifficultyType() {
        difficultyType = DifficultyTypes.EASY;
    }

    public String getSceneName() {
        return sceneName;
    }

    public static SoundTypes getSound() {
        return soundType;
    }

    public static int nextSoundType() {
        soundType = soundType.next();
        return soundType.ordinal();
    }

    public static void dropSoundType() {
        soundType = SoundTypes.ON;
    }

    public static String[] getAllSceneNames() {
        List<String> list = new ArrayList<>();
        for (SceneItem sceneItem : gameScenes)
            list.add(sceneItem.getSceneName());
        String result[] = new String[list.size()];
        return list.toArray(result);
    }
};
