package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.players.virtual.EffectiveVirtualNonAdaptivePlayer;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class CompetitionDemoScene extends AbstractMultiplayerScene {

    private static final long GAMEOVER_PAUSE = 3000;

    public CompetitionDemoScene(final int width, final int height, final int[] prognosis, final int figureSize, final YuckTypes yuckType,
            final RandomTypes randomType, final DifficultyTypes difficulty) {

        super(SceneItem.COMPETITION_DEMO, width, height, prognosis, figureSize, yuckType, randomType, difficulty);

        first = new EffectiveVirtualNonAdaptivePlayer<>(game[0], Const.REACTIONS_EATER);
        second = new VirtualAdaptivePlayer<>(game[1], Const.FULLNESS_EATER);
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
            nextScene(SceneItem.COMPETITION_DEMO);
    }

    @Override
    protected void checkAuto() {
        if (pauseBetweenGames > 0 && (System.currentTimeMillis() - pauseBetweenGames > GAMEOVER_PAUSE))
            nextScene(SceneItem.MENU);
    }

    @Override
    protected void changes() {
        super.changes();
    }

    @Override
    protected void start() {
    }
}
