package stackup.scenes;

import stackup.players.virtual.EffectiveVirtualNonAdaptivePlayer;
import stackup.players.virtual.VirtualAdaptivePlayer;


public class TwoPlayersDemo extends AbstractMultiPlayerGame {

    private static final long GAMEOVER_PAUSE = 3000;

    public TwoPlayersDemo(final int width, final int height, final int[] forecast, final int lenght) {
        super(Scene.TWO_PLAYERS_DEMO, width, height, forecast, lenght, 2);

        new EffectiveVirtualNonAdaptivePlayer(game[0]);
        new VirtualAdaptivePlayer(game[1]);
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }

    @Override
    protected void terminate() {
        super.terminate();
        if (!escapePressed)
            nextScene(Scene.TWO_PLAYERS_DEMO);
    }

    @Override
    protected void checkAuto() {
        if (pauseBetweenGames > 0 && (System.currentTimeMillis() - pauseBetweenGames > GAMEOVER_PAUSE))
            nextScene(Scene.MENU);
    }

    @Override
    protected void changes() {
        super.changes();
    }
}
