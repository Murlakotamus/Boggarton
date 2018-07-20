package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.players.virtual.EffectiveVirtualNonAdaptivePlayer;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;
import com.foxcatgames.boggarton.players.virtual.solver.Price;

public class TwoPlayersDemo extends AbstractMultiplayerGame {

    private static final long GAMEOVER_PAUSE = 3000;

    public TwoPlayersDemo(final int width, final int height, final int[] forecast, final int lenght,
            final Yucks yuckType) {
        super(SceneItem.TWO_PLAYERS_DEMO, width, height, forecast, lenght, 2, yuckType);

        new EffectiveVirtualNonAdaptivePlayer(game[0], new Price());
        new VirtualAdaptivePlayer(game[1], new Price());
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
            nextScene(SceneItem.TWO_PLAYERS_DEMO);
    }

    @Override
    protected void checkAuto() {
        if (pauseBetweenGames > 0
                && (System.currentTimeMillis() - pauseBetweenGames > GAMEOVER_PAUSE))
            nextScene(SceneItem.MENU);
    }

    @Override
    protected void changes() {
        super.changes();
    }
}
