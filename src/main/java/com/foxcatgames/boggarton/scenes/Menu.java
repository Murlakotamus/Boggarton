package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.DARK_FONT;
import static com.foxcatgames.boggarton.Const.FONT_HEIGHT;
import static com.foxcatgames.boggarton.Const.FONT_WIDTH;
import static com.foxcatgames.boggarton.Const.LIGHT_FONT;
import static com.foxcatgames.boggarton.Const.SCREEN_WIDTH;
import static com.foxcatgames.boggarton.Const.TITLE;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.Forecast;

public class Menu extends AbstractLogo {

    static private final MenuItem[] ITEMS = MenuItem.values();
    static private final int ITEMS_NUMBER = MenuItem.values().length;
    static private final int Y_POS_MENU = 190;
    static private final int Y_INTERVAL = FONT_HEIGHT + 1;
    static private final int MIN_DIFFICULTY = 4;
    static private final int MAX_DIFFICULTY = 7;
    static private final int MIN_SIZE = 3;
    static private final int MAX_SIZE = 6;
    static private final int MIN_PROGNOSIS = 1;
    static private final int MAX_PROGNOSIS = 3;

    private final SimpleEntity title = new SimpleEntity(TITLE, layer);
    private final Text[] passive = new Text[ITEMS_NUMBER];
    private final Text[] active = new Text[ITEMS_NUMBER];
    private final Text[] yuckActive = new Text[MenuItem.YUCKS.getValues().length];
    private final Text[] yuckPassive = new Text[MenuItem.YUCKS.getValues().length];
    private final Brick[] brickSet = new Brick[MAX_DIFFICULTY];

    private Forecast forecast = null;
    private int currentPosition = 0;

    public Menu() {
        super(Scene.MENU);

        int i = 0;
        for (MenuItem item : ITEMS) {
            passive[i] = new Text(item.getName(), DARK_FONT, layer);
            active[i++] = new Text(item.getName(), LIGHT_FONT, layer);
        }

        i = 0;
        for (String item : MenuItem.YUCKS.getValues()) {
            yuckPassive[i] = new Text(item, DARK_FONT, layer);
            yuckActive[i++] = new Text(item, LIGHT_FONT, layer);
        }

        for (i = 0; i < MAX_DIFFICULTY; i++)
            brickSet[i] = new Brick(11 + i, layer);

        drawMenu();
        drawPrognosis();
        addKeyHandlers();
    }

    private void drawMenu() {
        title.spawn(new Vector2f(TITLE_X, TITLE_Y));

        int pointY = Y_POS_MENU;
        for (int i = 0; i < ITEMS_NUMBER; i++) {
            int pointX = (SCREEN_WIDTH / 2) - ((ITEMS[i].getName().length() * FONT_WIDTH) / 2);
            if (i == currentPosition) {
                passive[i].unspawn();
                active[i].spawn(new Vector2f(pointX, pointY += Y_INTERVAL));
                if (i == MenuItem.YUCKS.ordinal()) {
                    int item = Scene.getYuckStrategy() ? 1 : 0;
                    yuckActive[1 - item].unspawn();
                    yuckPassive[item].unspawn();
                    yuckActive[item].spawn(new Vector2f(
                            pointX + (((ITEMS[i].getName().length() - 4) * FONT_WIDTH)), pointY));
                }
            } else {
                active[i].unspawn();
                passive[i].spawn(new Vector2f(pointX, pointY += Y_INTERVAL));
                if (i == MenuItem.YUCKS.ordinal()) {
                    int item = Scene.getYuckStrategy() ? 1 : 0;
                    yuckPassive[1 - item].unspawn();

                    yuckActive[item].unspawn();
                    yuckPassive[item].spawn(new Vector2f(
                            pointX + (((ITEMS[i].getName().length() - 4) * FONT_WIDTH)), pointY));
                }
            }

        }
    }

    private void drawPrognosis() {
        if (forecast != null)
            forecast.unspawn();

        forecast = new Forecast(layer,
                new Vector2f((SCREEN_WIDTH / 2) - (size * BOX / 2) - BORDER,
                        ITEMS_NUMBER * Y_INTERVAL + BOX + Y_POS_MENU),
                prognosis, size, difficulty, true);
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
                final Scene scene = ITEMS[currentPosition].getScene();
                switch (scene) {
                case YUCKS:
                    Scene.changeYucksStrategy();
                    drawMenu();
                    break;
                case DIFFICULTY:
                    difficulty = changeParam(difficulty, MIN_DIFFICULTY, MAX_DIFFICULTY);
                    drawPrognosis();
                    break;
                case SIZE:
                    size = changeParam(size, MIN_SIZE, MAX_SIZE);
                    drawPrognosis();
                    break;
                case PROGNOSIS:
                    prognosis = changeParam(prognosis, MIN_PROGNOSIS, MAX_PROGNOSIS);
                    drawPrognosis();
                    break;
                default:
                    nextScene = scene;
                }
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_RETURN, enter);
    }

    private int changeParam(int param, int min, int max) {
        if (++param > max)
            param = min;
        return param;
    }

    private void addKeyEscape() {
        final KeyListener escape = new KeyListener() {
            @Override
            public void onKeyUp() {
                nextScene(Scene.OUTRO);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }
}
