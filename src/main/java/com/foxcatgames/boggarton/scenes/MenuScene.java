package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.forecast.MenuForecast;

public class MenuScene extends AbstractLogoScene {

    static private final String CONFIG = "config.ini";

    static private final MenuItem[] ITEMS = MenuItem.values();
    static private final int ITEMS_NUMBER = MenuItem.values().length;
    static private final int Y_POS_MENU = 200;
    static private final int Y_INTERVAL = FONT_HEIGHT + 1;

    private final SimpleEntity title = new SimpleEntity(TITLE, layer);
    private final Text[] passive = new Text[ITEMS_NUMBER];
    private final Text[] active = new Text[ITEMS_NUMBER];
    private final Brick[] brickSet = new Brick[MAX_DIFFICULTY];

    private MenuForecast forecast = null;
    private int currentPosition = 0;

    public MenuScene() {
        super(SceneItem.MENU);
        restoreSettings();

        int i = 0;
        for (MenuItem item : ITEMS) {
            passive[i] = new Text(item.getName(), DARK_FONT, layer);
            active[i++] = new Text(item.getName(), LIGHT_FONT, layer);
        }

        for (i = 0; i < MAX_DIFFICULTY; i++)
            brickSet[i] = new Brick(10 * Const.CURRENT_SET + 1 + i, layer);

        drawMenu();
        drawPrognosis();
        addKeyHandlers();
    }

    private void restoreSettings() {
        try (final BufferedReader in = new BufferedReader(new FileReader(new File(CONFIG)))) {
            final Properties props = new Properties();
            props.load(in);
            for (final String key : props.stringPropertyNames()) {
                int value = Integer.parseInt(props.getProperty(key));
                switch (key) {
                case "MODE":
                    final MenuItem mode = MenuItem.MODE;
                    SceneItem.dropStartScene();
                    for (int i = 0; i < value; i++)
                        mode.setPosition(SceneItem.nextStartScene());
                    break;
                case "YUCKS":
                    final MenuItem yucks = MenuItem.YUCKS;
                    SceneItem.dropYucksType();
                    for (int i = 0; i < value; i++)
                        yucks.setPosition(SceneItem.nextYucksType());
                    break;
                case "RANDOM_TYPE":
                    final MenuItem bricks = MenuItem.RANDOM_TYPE;
                    SceneItem.dropRandomType();
                    for (int i = 0; i < value; i++)
                        bricks.setPosition(SceneItem.nextRandomType());
                    break;
                case "DIFFICULTY":
                    difficulty = setParam(value, MIN_DIFFICULTY, MAX_DIFFICULTY);
                    break;
                case "FIGURE_SIZE":
                    figureSize = setParam(value, MIN_SIZE, MAX_SIZE);
                    break;
                case "PROGNOSIS":
                    prognosis = setParam(value, MIN_PROGNOSIS, MAX_PROGNOSIS);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSettings() {
        final File file = new File(CONFIG);
        try (FileOutputStream fos = new FileOutputStream(file); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            file.createNewFile();
            final Properties props = new Properties();

            props.setProperty(MenuItem.MODE.name(), "" + MenuItem.MODE.getPosition());
            props.setProperty(MenuItem.YUCKS.name(), "" + MenuItem.YUCKS.getPosition());
            props.setProperty(MenuItem.RANDOM_TYPE.name(), "" + MenuItem.RANDOM_TYPE.getPosition());
            props.setProperty(MenuItem.DIFFICULTY.name(), "" + difficulty);
            props.setProperty(MenuItem.FIGURE_SIZE.name(), "" + figureSize);
            props.setProperty(MenuItem.PROGNOSIS.name(), "" + prognosis);

            props.store(bw, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawMenu() {
        title.spawn(new Vector2f(TITLE_X, TITLE_Y));
        final int pointX = 510;
        int pointY = Y_POS_MENU;
        for (int i = 0; i < ITEMS_NUMBER; i++) {
            final MenuItem item = MenuItem.values()[i];
            if (i == currentPosition) {
                passive[i].unspawn();
                if (item.getValues() != null) {
                    active[i].unspawn();
                    active[i] = new Text(item.getName() + ": " + item.getValues()[item.getPosition()], LIGHT_FONT, layer);
                }
                active[i].spawn(new Vector2f(pointX, pointY += Y_INTERVAL));

            } else {
                active[i].unspawn();
                if (item.getValues() != null) {
                    passive[i].unspawn();
                    passive[i] = new Text(item.getName() + ": " + item.getValues()[item.getPosition()], DARK_FONT, layer);
                }
                passive[i].spawn(new Vector2f(pointX, pointY += Y_INTERVAL));
            }
        }
    }

    private void drawPrognosis() {
        if (forecast != null)
            forecast.unspawn();

        forecast = new MenuForecast(layer, new Vector2f(470 - BOX * figureSize + 10, ITEMS_NUMBER * Y_INTERVAL + BOX + Y_POS_MENU - 170), prognosis, figureSize,
                difficulty);
    }

    private void addKeyHandlers() {
        addKeyUp();
        addKeyDown();
        addKeyEnter();
        addKeyEscape();
    }

    private void addKeyUp() {
        final KeyListener up = new KeyListener() {
            @Override
            public void onKeyUp() {
                if (--currentPosition < 0)
                    currentPosition = ITEMS_NUMBER - 1;
                drawMenu();
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_UP, up);
    }

    private void addKeyDown() {
        final KeyListener down = new KeyListener() {
            @Override
            public void onKeyUp() {
                if (++currentPosition >= ITEMS_NUMBER)
                    currentPosition = 0;
                drawMenu();
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_DOWN, down);
    }

    private void addKeyEnter() {
        final KeyListener enter = new KeyListener() {
            @Override
            public void onKeyUp() {
                final MenuItem menuItem = ITEMS[currentPosition];
                switch (menuItem) {
                case START:
                    nextScene = SceneItem.getStartScene();
                    break;
                case MODE:
                    menuItem.setPosition(SceneItem.nextStartScene());
                    drawMenu();
                    break;
                case YUCKS:
                    menuItem.setPosition(SceneItem.nextYucksType());
                    drawMenu();
                    break;
                case RANDOM_TYPE:
                    menuItem.setPosition(SceneItem.nextRandomType());
                    drawMenu();
                    break;
                case DIFFICULTY:
                    difficulty = changeParam(difficulty, MIN_DIFFICULTY, MAX_DIFFICULTY);
                    drawPrognosis();
                    break;
                case FIGURE_SIZE:
                    figureSize = changeParam(figureSize, MIN_SIZE, MAX_SIZE);
                    drawPrognosis();
                    break;
                case PROGNOSIS:
                    prognosis = changeParam(prognosis, MIN_PROGNOSIS, MAX_PROGNOSIS);
                    drawPrognosis();
                    break;
                default:
                    nextScene = SceneItem.ABOUT;
                }
                saveSettings();
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_RETURN, enter);
    }

    private int changeParam(int param, final int min, final int max) {
        if (++param > max)
            param = min;
        return param;
    }

    private int setParam(int param, final int min, final int max) {
        if (param > max)
            param = min;
        return param;
    }

    private void addKeyEscape() {
        final KeyListener escape = new KeyListener() {
            @Override
            public void onKeyUp() {
                nextScene(SceneItem.OUTRO);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }

    @Override
    protected void start() {
    }
}
