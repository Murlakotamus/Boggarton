package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.*;

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

    private void drawMenu() {
        title.spawn(new Vector2f(TITLE_X, TITLE_Y));
        int pointX = 510;
        int pointY = Y_POS_MENU;
        for (int i = 0; i < ITEMS_NUMBER; i++) {
            MenuItem item = MenuItem.values()[i];
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

        forecast = new MenuForecast(layer, new Vector2f(470 - BOX * size + 10, ITEMS_NUMBER * Y_INTERVAL + BOX + Y_POS_MENU - 170), prognosis, size,
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
                final MenuItem scene = ITEMS[currentPosition];
                switch (scene) {
                case START:
                    nextScene = SceneItem.getStartScene();
                    break;
                case MODE:
                    scene.nextPosition();
                    SceneItem.nextStartScene();
                    drawMenu();
                    break;
                case YUCKS:
                    scene.nextPosition();
                    SceneItem.changeYucksStrategy();
                    drawMenu();
                    break;
                case DIFFICULTY:
                    difficulty = changeParam(difficulty, MIN_DIFFICULTY, MAX_DIFFICULTY);
                    drawPrognosis();
                    break;
                case FIGURE_SIZE:
                    size = changeParam(size, MIN_SIZE, MAX_SIZE);
                    drawPrognosis();
                    break;
                case PROGNOSIS:
                    prognosis = changeParam(prognosis, MIN_PROGNOSIS, MAX_PROGNOSIS);
                    drawPrognosis();
                    break;
                default:
                    nextScene = SceneItem.ABOUT;
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
                nextScene(SceneItem.OUTRO);
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_ESCAPE, escape);
    }
}
