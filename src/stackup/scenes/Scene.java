package stackup.scenes;

import static stackup.Const.HEIGHT;
import static stackup.Const.WIDTH;

public enum Scene {
    INTRO, MENU, ONE_PLAYER_GAME, ONE_PLAYER_DEMO, PLAYER_VS_COMP, TWO_PLAYERS_GAME, TWO_PLAYERS_DEMO, YUCKS, DIFFICULTY, SIZE, PROGNOSIS, ABOUT, OUTRO, FINISH_GAME;

    private static final int[] FORECASTS = { AbstractScene.prognosis, AbstractScene.prognosis };
    private static boolean yuckStrategy = true;

    public AbstractScene createScene() {
        switch (this) {
        case INTRO:
            return new Intro();
        case MENU:
            return new Menu();
        case ONE_PLAYER_GAME:
            return new OnePlayerGame(WIDTH, HEIGHT, AbstractScene.prognosis, AbstractScene.size);
        case ONE_PLAYER_DEMO:
            return new OnePlayerDemo(WIDTH, HEIGHT, 2, AbstractScene.size);
        case PLAYER_VS_COMP:
            return new PlayerVsCompGame(WIDTH, HEIGHT, FORECASTS, AbstractScene.size, yuckStrategy);
        case TWO_PLAYERS_GAME:
            return new PlayerVsPlayerGame(WIDTH, HEIGHT, FORECASTS, AbstractScene.size, yuckStrategy);
        case TWO_PLAYERS_DEMO:
            return new TwoPlayersDemo(WIDTH, HEIGHT, new int[] { 3, 2 }, AbstractScene.size, yuckStrategy);
        case OUTRO:
            return new Outro();
        default:
            return new About();
        }
    }

    public static boolean changeYucksStrategy() {
        yuckStrategy = !yuckStrategy;
        return yuckStrategy;
    }

    public static boolean getYuckStrategy() {
        return yuckStrategy;
    }
};
