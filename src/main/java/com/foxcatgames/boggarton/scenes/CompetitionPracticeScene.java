package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.players.real.RealMultiplayer;
import com.foxcatgames.boggarton.players.virtual.EffectiveVirtualAdaptivePlayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class CompetitionPracticeScene extends AbstractMultiplayerScene {

    public CompetitionPracticeScene(final int width, final int height, final int[] prognosis, final int figureSize, final YuckTypes yuckType,
            final RandomTypes randomType, final DifficultyTypes difficulty) {

        super(SceneItem.COMPETITION_PRACTICE, width, height, prognosis, figureSize, yuckType, randomType, difficulty, new boolean[] { true, false });

        first = new EffectiveVirtualAdaptivePlayer<>(game[0], Const.FULLNESS_EATER);
        second = new RealMultiplayer(game[1], Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_UP);
    }

    @Override
    protected void checkAuto() {
    }

    @Override
    protected void changes() {
        super.changes();
    }

    @Override
    protected void start() {
    }
}
