package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.CONFIG;
import static com.foxcatgames.boggarton.Const.HEIGHT;
import static com.foxcatgames.boggarton.Const.MAX_PROGNOSIS;
import static com.foxcatgames.boggarton.Const.MAX_SIZE;
import static com.foxcatgames.boggarton.Const.MIN_PROGNOSIS;
import static com.foxcatgames.boggarton.Const.MIN_SIZE;
import static com.foxcatgames.boggarton.Const.WIDTH;
import static com.foxcatgames.boggarton.scenes.MenuItem.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.game.utils.Utils;
import com.foxcatgames.boggarton.scenes.gamescenes.CompetitionDemoScene;
import com.foxcatgames.boggarton.scenes.gamescenes.CompetitionGameScene;
import com.foxcatgames.boggarton.scenes.gamescenes.CompetitionPracticeScene;
import com.foxcatgames.boggarton.scenes.gamescenes.DemoScene;
import com.foxcatgames.boggarton.scenes.gamescenes.GameScene;
import com.foxcatgames.boggarton.scenes.gamescenes.PracticeScene;
import com.foxcatgames.boggarton.scenes.gamescenes.ReplayScene;
import com.foxcatgames.boggarton.scenes.types.*;

public enum SceneItem implements IName {
    INTRO, MENU, GAME("Game"), GAME_TYPE("Game type"), PRACTICE("Practice"), COMPETITION("Competition"), COMPETITION_PRACTICE("Practice with computer"), DEMO("Demo"), COMPETITION_DEMO(
            "Competition demo"), REPLAY("Replay game"), ABOUT, OUTRO, FINISH_GAME;

    static final List<SceneItem> gameScenes = Arrays.asList(GAME, GAME_TYPE, PRACTICE, COMPETITION, COMPETITION_PRACTICE, DEMO, COMPETITION_DEMO, REPLAY);

    public static int prognosis = 3;
    public static int figureSize = 3;

    private final String sceneName;
    private static final int[] PROGNOSISES = {prognosis, prognosis};
    private static final int PROGNOSIS_EFFECTIVE = 4;
    private static final int PROGNOSIS_COMPLEX = 2;

    static GameTypes gameType = GameTypes.HORIZONTAL;
    static YuckTypes yuckType = YuckTypes.RANDOM;
    static RandomTypes randomType = RandomTypes.RANDOM;
    static DifficultyTypes difficultyType = DifficultyTypes.EASY;
    static SoundTypes soundType = SoundTypes.ON;

    SceneItem() {
        sceneName = null;
    }

    SceneItem(final String sceneName) {
        this.sceneName = sceneName;
    }

    public AbstractScene createScene() {
        SceneItem.restoreSettings();
        switch (this) {
            case INTRO:
                Sound.playLogo();
                return new IntroScene();
            case MENU:
                return new MenuScene();
            case GAME:
                return new GameScene(WIDTH, HEIGHT, prognosis, figureSize, randomType, difficultyType, gameType);
            case PRACTICE:
                return new PracticeScene(WIDTH, HEIGHT, prognosis, figureSize, randomType, difficultyType);
            case DEMO:
                return new DemoScene(WIDTH, HEIGHT, PROGNOSIS_COMPLEX, figureSize, randomType, difficultyType);
            case COMPETITION_PRACTICE:
                return new CompetitionPracticeScene(WIDTH, HEIGHT, PROGNOSISES, figureSize, yuckType, randomType, difficultyType);
            case COMPETITION:
                return new CompetitionGameScene(WIDTH, HEIGHT, PROGNOSISES, figureSize, yuckType, randomType, difficultyType);
            case COMPETITION_DEMO:
                return new CompetitionDemoScene(WIDTH, HEIGHT, new int[]{PROGNOSIS_EFFECTIVE, PROGNOSIS_COMPLEX},
                        figureSize, yuckType, randomType, difficultyType);
            case REPLAY:
                return new ReplayScene(WIDTH, HEIGHT);
            case OUTRO:
                return new OutroScene();
            default:
                return new AboutScene();
        }
    }

    public static void restoreSettings() {
        final File configFile = new File(CONFIG);
        if (!configFile.exists())
            return;

        try (final BufferedReader in = new BufferedReader(new FileReader(CONFIG))) {
            final Properties props = new Properties();
            props.load(in);
            for (final String key : props.stringPropertyNames()) {
                final int position = Integer.parseInt(props.getProperty(key));
                switch (key) {
                    case "GAME_TYPE":
                        gameType = Utils.getValue(GameTypes.class, position);
                        BOGGARTON_TYPE.setSubmenuPosition(gameType);
                        break;
                    case "MODE":
                        MODE.setSubmenuPosition(position);
                        break;
                    case "YUCKS":
                        yuckType = Utils.getValue(YuckTypes.class, position);
                        YUCKS.setSubmenuPosition(yuckType);
                        break;
                    case "RANDOM_TYPE":
                        randomType = Utils.getValue(RandomTypes.class, position);
                        RANDOM_TYPE.setSubmenuPosition(randomType);
                        break;
                    case "DIFFICULTY":
                        difficultyType = Utils.getValue(DifficultyTypes.class, position);
                        DIFFICULTY.setSubmenuPosition(difficultyType);
                        break;
                    case "SOUND":
                        soundType = Utils.getValue(SoundTypes.class, position);
                        SOUND.setSubmenuPosition(soundType);
                        break;
                    case "FIGURE_SIZE":
                        figureSize = setValue(position, MIN_SIZE, MAX_SIZE);
                        break;
                    case "PROGNOSIS":
                        prognosis = setValue(position, MIN_PROGNOSIS, MAX_PROGNOSIS);
                        break;
                }
            }
        } catch (final IOException e) {
            Logger.printStackTrace(e);
        }
    }

    static void saveSettings() {
        final File file = new File(CONFIG);
        try (final FileOutputStream fos = new FileOutputStream(file);
             final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            file.createNewFile();
            final Properties props = new Properties();

            props.setProperty(GAME_TYPE.name(), "" + BOGGARTON_TYPE.getSubmenuElementPosition());
            props.setProperty(MODE.name(), "" + MODE.getSubmenuElementPosition());
            props.setProperty(YUCKS.name(), "" + YUCKS.getSubmenuElementPosition());
            props.setProperty(RANDOM_TYPE.name(), "" + RANDOM_TYPE.getSubmenuElementPosition());
            props.setProperty(DIFFICULTY.name(), "" + DIFFICULTY.getSubmenuElementPosition());
            props.setProperty(FIGURE_SIZE.name(), "" + figureSize);
            props.setProperty(PROGNOSIS.name(), "" + prognosis);
            props.setProperty(SOUND.name(), "" + SOUND.getSubmenuElementPosition());

            props.store(bw, "");
        } catch (final IOException e) {
            Logger.printStackTrace(e);
        }
    }

    private static int setValue(int param, final int min, final int max) {
        if (param > max)
            param = min;
        return param;
    }

    public static int getSetSize() {
        return difficultyType.getSetSize();
    }

    public static SoundTypes getSound() {
        return soundType;
    }

    public static String[] getNames() {
        return Utils.getNames(SceneItem.class);
    }

    @Override
    public String getName() {
        return sceneName;
    }

    public static <E extends Enum<E> & IMenu<E>> E getRelativeEnumValue(final E enumValue, final int nextPosition) {
        final String className = enumValue.getClass().getSimpleName();
        switch (className) {
            case "GameTypes":
                gameType = gameType.relative(nextPosition);
                break;
            case "YuckTypes":
                yuckType = yuckType.relative(nextPosition);
                break;
            case "RandomTypes":
                randomType = randomType.relative(nextPosition);
                break;
            case "DifficultyTypes":
                difficultyType = difficultyType.relative(nextPosition);
                break;
            case "SoundTypes":
                soundType = soundType.relative(nextPosition);
        }
        return enumValue.relative(nextPosition);
    }

}
