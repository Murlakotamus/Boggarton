package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.CONFIG;
import static com.foxcatgames.boggarton.Const.HEIGHT;
import static com.foxcatgames.boggarton.Const.MAX_PROGNOSIS;
import static com.foxcatgames.boggarton.Const.MAX_SIZE;
import static com.foxcatgames.boggarton.Const.MIN_PROGNOSIS;
import static com.foxcatgames.boggarton.Const.MIN_SIZE;
import static com.foxcatgames.boggarton.Const.WIDTH;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.SoundTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public enum SceneItem {
    INTRO, MENU, GAME("Game"), PRACTICE("Practice"), DEMO("Demo"), COMPETITION_PRACTICE("Practice with computer"), COMPETITION("Competition"), COMPETITION_DEMO(
            "Competition demo"), REPLAY("Replay game"), ABOUT, OUTRO, FINISH_GAME;

    private static List<SceneItem> gameScenes = Arrays.asList(GAME, PRACTICE, COMPETITION, COMPETITION_PRACTICE, DEMO, COMPETITION_DEMO, REPLAY);

    protected static int prognosis = 3;
    protected static int figureSize = 3;

    private final String sceneName;
    private static final int[] PROGNOSIS = { prognosis, prognosis };
    private static final int PROGNOSIS_EFFECTIVE = 4;
    private static final int PROGNOSIS_COMPLEX = 2;

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
            return new GameScene(WIDTH, HEIGHT, prognosis, figureSize, randomType, difficultyType);
        case PRACTICE:
            return new PracticeScene(WIDTH, HEIGHT, prognosis, figureSize, randomType, difficultyType);
        case DEMO:
            return new DemoScene(WIDTH, HEIGHT, PROGNOSIS_COMPLEX, figureSize, randomType, difficultyType);
        case COMPETITION_PRACTICE:
            return new CompetitionPracticeScene(WIDTH, HEIGHT, PROGNOSIS, figureSize, yuckType, randomType, difficultyType);
        case COMPETITION:
            return new CompetitionGameScene(WIDTH, HEIGHT, PROGNOSIS, figureSize, yuckType, randomType, difficultyType);
        case COMPETITION_DEMO:
            return new CompetitionDemoScene(WIDTH, HEIGHT, new int[] { PROGNOSIS_EFFECTIVE, PROGNOSIS_COMPLEX }, figureSize, yuckType, randomType,
                    difficultyType);
        case REPLAY:
            return new ReplayScene(WIDTH, HEIGHT, figureSize);
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

    protected static void restoreSettings() {
        final File configFile = new File(CONFIG);
        if (!configFile.exists())
            return;

        try (final BufferedReader in = new BufferedReader(new FileReader(new File(CONFIG)))) {
            final Properties props = new Properties();
            props.load(in);
            for (final String key : props.stringPropertyNames()) {
                int value = Integer.parseInt(props.getProperty(key));
                switch (key) {
                case "MODE":
                    final MenuItem mode = MenuItem.MODE;
                    dropStartScene();
                    for (int i = 0; i < value; i++)
                        mode.setSubmenuElementPosition(nextStartScene());
                    break;
                case "YUCKS":
                    final MenuItem yucks = MenuItem.YUCKS;
                    dropYucksType();
                    for (int i = 0; i < value; i++)
                        yucks.setSubmenuElementPosition(nextYucksType());
                    break;
                case "RANDOM_TYPE":
                    final MenuItem bricks = MenuItem.RANDOM_TYPE;
                    dropRandomType();
                    for (int i = 0; i < value; i++)
                        bricks.setSubmenuElementPosition(nextRandomType());
                    break;
                case "DIFFICULTY":
                    final MenuItem difficulty = MenuItem.DIFFICULTY;
                    dropDifficultyType();
                    for (int i = 0; i < value; i++)
                        difficulty.setSubmenuElementPosition(nextDifficultyType());
                    break;
                case "FIGURE_SIZE":
                    figureSize = setValue(value, MIN_SIZE, MAX_SIZE);
                    break;
                case "PROGNOSIS":
                    prognosis = setValue(value, MIN_PROGNOSIS, MAX_PROGNOSIS);
                    break;
                case "SOUND":
                    final MenuItem sound = MenuItem.SOUND;
                    dropSoundType();
                    for (int i = 0; i < value; i++)
                        sound.setSubmenuElementPosition(nextSoundType());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void saveSettings() {
        final File file = new File(CONFIG);
        try (FileOutputStream fos = new FileOutputStream(file); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            file.createNewFile();
            final Properties props = new Properties();

            props.setProperty(MenuItem.MODE.name(), "" + MenuItem.MODE.getSubmenuElementPosition());
            props.setProperty(MenuItem.YUCKS.name(), "" + MenuItem.YUCKS.getSubmenuElementPosition());
            props.setProperty(MenuItem.RANDOM_TYPE.name(), "" + MenuItem.RANDOM_TYPE.getSubmenuElementPosition());
            props.setProperty(MenuItem.DIFFICULTY.name(), "" + MenuItem.DIFFICULTY.getSubmenuElementPosition());
            props.setProperty(MenuItem.FIGURE_SIZE.name(), "" + figureSize);
            props.setProperty(MenuItem.PROGNOSIS.name(), "" + prognosis);
            props.setProperty(MenuItem.SOUND.name(), "" + MenuItem.SOUND.getSubmenuElementPosition());

            props.store(bw, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int setValue(int param, final int min, final int max) {
        if (param > max)
            param = min;
        return param;
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
