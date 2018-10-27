package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.AutomatedMultiplayerGame;
import com.foxcatgames.boggarton.game.MultiplayerGame;
import com.foxcatgames.boggarton.game.utils.Victories;
import com.foxcatgames.boggarton.players.real.RealMultiplayer;
import com.foxcatgames.boggarton.players.virtual.EffectiveVirtualAdaptivePlayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class CompetitionPracticeScene extends AbstractMultiplayerScene {

    private final AutomatedMultiplayerGame leftGame;
    private final MultiplayerGame rightGame;

    public CompetitionPracticeScene(final int width, final int height, final int[] prognosis, final int figureSize, final YuckTypes yuckType,
            final RandomTypes randomType, final DifficultyTypes difficulty) {

        super(SceneItem.COMPETITION_PRACTICE, width, figureSize);

        leftGame = new AutomatedMultiplayerGame(layer, X, Y, width, height, prognosis[0], figureSize, difficulty.getSetSize(), Victories.getVictories(0),
                yuckType, randomType, Const.SOUNDS_LEFT);
        leftGame.setName(PLAYERS_NAMES[0]);

        rightGame = new MultiplayerGame(layer, X + 446, Y, width, height, prognosis[1], figureSize, difficulty.getSetSize(), Victories.getVictories(1),
                yuckType, randomType, Const.SOUNDS_RIGHT);
        rightGame.setName(PLAYERS_NAMES[0]);

        leftGame.startGame();
        rightGame.startGame();

        leftPlayer = new EffectiveVirtualAdaptivePlayer<>(leftGame, prognosis[0], Const.FULLNESS_EATER);
        rightPlayer = new RealMultiplayer(rightGame, Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_UP);
    }

    private MultiplayerGame[] getGames() {
        return new MultiplayerGame[] { leftGame, rightGame };
    }

    @Override
    protected void changes() {
        changes(getGames());
    }

    @Override
    protected void hideGlass() {
        hideGlass(getGames());
    }

    @Override
    protected void showGlass() {
        hideGlass(getGames());
    }

    @Override
    protected void start() {
    }
}
