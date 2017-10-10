package stackup.scenes;

import static stackup.Const.BOX;
import static stackup.Const.DARK_FONT;
import static stackup.Const.FONT_HEIGHT;
import static stackup.Const.FONT_WIDTH;
import static stackup.Const.LIGHT_FONT;
import static stackup.Const.SCREEN_WIDTH;
import static stackup.Const.TITLE;
import static stackup.Const.WIDTH;
import static stackup.Const.BORDER;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import stackup.engine.EventManager;
import stackup.engine.KeyListener;
import stackup.entity.Brick;
import stackup.entity.SimpleEntity;
import stackup.entity.Text;
import stackup.game.Forecast;

public class Menu extends AbstractLogo {

    static private final MenuItem[] ITEMS = MenuItem.values();
    static private final int ITEMS_NUMBER = MenuItem.values().length;

    static private final int Y_POS_MENU = 170;
    static private final int Y_INTERVAL = FONT_HEIGHT + 1;

    static private final int MIN_SET_SIZE = 4;
    static private final int MAX_SET_SIZE = 7;
    static private final int MIN_FIGURE_SIZE = 2;

    private final SimpleEntity title = new SimpleEntity(TITLE, layer);
    private final Text[] passive = new Text[ITEMS_NUMBER];
    private final Text[] active = new Text[ITEMS_NUMBER];
    private final Brick[] brickSet = new Brick[MAX_SET_SIZE];

    private Forecast forecast = null;

    private int currentPosition = 0;

    public Menu() {
        super(Scene.MENU);

        int i = 0;
        for (MenuItem item : ITEMS) {
            passive[i] = new Text(item.getName(), DARK_FONT, layer);
            active[i++] = new Text(item.getName(), LIGHT_FONT, layer);
        }
        for (i = 0; i < MAX_SET_SIZE; i++)
            brickSet[i] = new Brick(11 + i, layer);

        drawMenu();
        drawBricks();
        drawForecast();
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
            } else {
                active[i].unspawn();
                passive[i].spawn(new Vector2f(pointX, pointY += Y_INTERVAL));
            }
        }
    }

    private void drawBricks() {
        for (int i = difficulty; i < MAX_SET_SIZE; i++)
            brickSet[i].unspawn();

        for (int i = 0; i < difficulty; i++) {
            int pointX = (SCREEN_WIDTH / 2) - (difficulty * BOX / 2) + BOX * i;
            brickSet[i].spawn(new Vector2f(pointX, ITEMS_NUMBER * Y_INTERVAL + BOX + Y_POS_MENU));
        }
    }

    private void drawForecast() {
        if (forecast != null)
            forecast.unspawn();

        forecast = new Forecast(layer,
                new Vector2f((SCREEN_WIDTH / 2) - (size * BOX / 2) - BORDER,
                        ITEMS_NUMBER * Y_INTERVAL + BOX * 2.5f + Y_POS_MENU),
                deepness, size, difficulty, true);
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
                case DIFFICULTY:
                    if (++difficulty > MAX_SET_SIZE)
                        difficulty = MIN_SET_SIZE;
                    drawBricks();
                    break;
                case SIZE:
                    if (++size > WIDTH)
                        size = MIN_FIGURE_SIZE;
                    drawForecast();
                    break;
                default:
                    nextScene = scene;
                }
            }
        };
        EventManager.getInstance().addListener(Keyboard.KEY_RETURN, enter);
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
