package com.foxcatgames.boggarton.scenes;

import static com.foxcatgames.boggarton.Const.HEIGHT;
import static com.foxcatgames.boggarton.Const.WIDTH;

public enum SceneItem {
    INTRO, MENU, ONE_PLAYER_GAME, ONE_PLAYER_DEMO, PLAYER_VS_COMP, TWO_PLAYERS_GAME, TWO_PLAYERS_DEMO, REPLAY_GAME, YUCKS, DIFFICULTY, SIZE, PROGNOSIS, ABOUT, OUTRO, FINISH_GAME;

    private static final int[] FORECASTS = { AbstractScene.prognosis, AbstractScene.prognosis };
    private static boolean yuckStrategy = true;

    public AbstractScene createScene() {
        switch (this) {
        case INTRO:
            return new IntroScene();
        case MENU:
            return new MenuScene();
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
        case REPLAY_GAME:
            return new Replay(WIDTH, HEIGHT, 3, AbstractScene.size);
        case OUTRO:
            return new OutroScene();
        default:
            return new AboutScene();
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
