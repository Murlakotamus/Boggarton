package stackup.scenes;

import static stackup.Const.FORECAST;
import static stackup.Const.HEIGHT;
import static stackup.Const.LENGHT;
import static stackup.Const.WIDTH;

public enum Scene {
    INTRO, MENU, ONE_PLAYER_GAME, ONE_PLAYER_DEMO, PLAYER_VS_COMP, TWO_PLAYERS_GAME, TWO_PLAYERS_DEMO, DIFFICULTY, ABOUT, OUTRO, FINISH_GAME;

    private static final int[] FORECASTS = { FORECAST, FORECAST };
    

    @SuppressWarnings("unused")
    public AbstractScene createScene() {
        Class c = String.class;
        if (WIDTH >= LENGHT)
            switch (this) {
            case INTRO:
                return new Intro();
            case MENU:
                return new Menu();
            case ONE_PLAYER_GAME:
                return new OnePlayer(WIDTH, HEIGHT, FORECAST, LENGHT);
            case ONE_PLAYER_DEMO:
                return new OnePlayerDemo(WIDTH, HEIGHT, FORECAST, LENGHT);
            case PLAYER_VS_COMP:
                return new PlayerVsComp(WIDTH, HEIGHT, FORECASTS, LENGHT);
            case TWO_PLAYERS_GAME:
                return new PlayerVsPlayer(WIDTH, HEIGHT, FORECASTS, LENGHT);
            case TWO_PLAYERS_DEMO:
                return new TwoPlayersDemo(WIDTH, HEIGHT, FORECASTS, LENGHT);
            case OUTRO:
                return new Outro();
            default:
                return new About();
            }
        return new Outro();
    }
};
