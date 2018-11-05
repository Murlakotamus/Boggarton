package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.engine.EventManager;
import com.foxcatgames.boggarton.engine.KeyListener;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.SimpleEntity;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.forecast.MenuForecast;
import com.foxcatgames.boggarton.game.utils.Utils;

public class MenuScene extends AbstractLogoScene {

    static private final MenuItem[] ITEMS = MenuItem.values();
    static private final int ITEMS_NUMBER = MenuItem.values().length;
    static private final int Y_POS_MENU = 200;
    static private final int Y_INTERVAL = FONT_HEIGHT + 1;

    private final SimpleEntity title = new SimpleEntity(TITLE, layer);
    private final Text[] passive = new Text[ITEMS_NUMBER];
    private final Text[] active = new Text[ITEMS_NUMBER];
    private final Brick[] brickSet = new Brick[MAX_DIFFICULTY];

    private MenuForecast forecast;
    private int currentPosition;

    public MenuScene() {
        super(SceneItem.MENU);
        SceneItem.restoreSettings();

        int i = 0;
        for (MenuItem item : ITEMS) {
            passive[i] = new Text(item.getName(), DARK_FONT, layer);
            active[i++] = new Text(item.getName(), LIGHT_FONT, layer);
        }

        for (i = 0; i < MAX_DIFFICULTY; i++)
            brickSet[i] = new Brick(10 * Const.CURRENT_SET + 1 + i, layer);

        drawMenu();
        drawPrognosis(SceneItem.getSetSize());
        addKeyHandlers();
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
                    active[i] = new Text(item.getName() + ": " + item.getValues()[item.getSubmenuElementPosition()], LIGHT_FONT, layer);
                }
                active[i].spawn(new Vector2f(pointX, pointY += Y_INTERVAL));

            } else {
                active[i].unspawn();
                if (item.getValues() != null) {
                    passive[i].unspawn();
                    passive[i] = new Text(item.getName() + ": " + item.getValues()[item.getSubmenuElementPosition()], DARK_FONT, layer);
                }
                passive[i].spawn(new Vector2f(pointX, pointY += Y_INTERVAL));
            }
        }
    }

    private void drawPrognosis(final int setSize) {
        if (forecast != null)
            forecast.unspawn();

        forecast = new MenuForecast(layer, new Vector2f(470 - BOX * SceneItem.figureSize + 10, ITEMS_NUMBER * Y_INTERVAL + BOX + Y_POS_MENU - 170),
                SceneItem.prognosis, SceneItem.figureSize, setSize);
    }

    private void addKeyHandlers() {
        addKeyUp();
        addKeyDown();
        addKeyEnter();
        addKeyEscape(SceneItem.OUTRO);
    }

    private static void playMove() {
        Sound.playMove();
    }

    private void addKeyUp() {
        final KeyListener up = new KeyListener() {
            @Override
            public void onKeyDown() {
                playMove();
            }

            @Override
            public void onKeyUp() {
                if (--currentPosition < 0)
                    currentPosition = ITEMS_NUMBER - 1;
                drawMenu();
            }
        };
        EventManager.addListener(Keyboard.KEY_UP, up);
    }

    private void addKeyDown() {
        final KeyListener down = new KeyListener() {
            @Override
            public void onKeyDown() {
                playMove();
            }

            @Override
            public void onKeyUp() {
                if (++currentPosition >= ITEMS_NUMBER)
                    currentPosition = 0;
                drawMenu();
            }
        };
        EventManager.addListener(Keyboard.KEY_DOWN, down);
    }

    private void addKeyEnter() {
        final KeyListener enter = new KeyListener() {
            @Override
            public void onKeyDown() {
                Sound.playSelect();
            }

            @Override
            public void onKeyUp() {
                final MenuItem menuItem = ITEMS[currentPosition];
                switch (menuItem) {
                case START:
                    nextScene = SceneItem.gameScenes.get(ITEMS[1].getSubmenuElementPosition());
                    break;
                case MODE:
                    menuItem.setSubmenuPosition(menuItem.getSubmenuElementPosition() + 1);
                    drawMenu();
                    break;
                case YUCKS:
                    SceneItem.yuckType = Utils.nextValue(SceneItem.yuckType);
                    menuItem.setSubmenuPosition(SceneItem.yuckType.ordinal());
                    drawMenu();
                    break;
                case RANDOM_TYPE:
                    SceneItem.randomType = Utils.nextValue(SceneItem.randomType);
                    menuItem.setSubmenuPosition(SceneItem.randomType.ordinal());
                    drawMenu();
                    break;
                case DIFFICULTY:
                    SceneItem.difficultyType = Utils.nextValue(SceneItem.difficultyType);
                    menuItem.setSubmenuPosition(SceneItem.difficultyType.ordinal());
                    drawMenu();
                    drawPrognosis(SceneItem.getSetSize());
                    break;
                case SOUND:
                    SceneItem.soundType = Utils.nextValue(SceneItem.soundType);
                    menuItem.setSubmenuPosition(SceneItem.soundType.ordinal());
                    drawMenu();
                    break;
                case FIGURE_SIZE:
                    SceneItem.figureSize = nextValue(SceneItem.figureSize, MIN_SIZE, MAX_SIZE);
                    drawPrognosis(SceneItem.getSetSize());
                    break;
                case PROGNOSIS:
                    SceneItem.prognosis = nextValue(SceneItem.prognosis, MIN_PROGNOSIS, MAX_PROGNOSIS);
                    drawPrognosis(SceneItem.getSetSize());
                    break;
                default:
                    nextScene = SceneItem.ABOUT;
                }
                SceneItem.saveSettings();
            }
        };
        EventManager.addListener(Keyboard.KEY_RETURN, enter);
    }

    private static int nextValue(int param, final int min, final int max) {
        if (++param > max)
            param = min;
        return param;
    }

    @Override
    protected void start() {
    }
}
