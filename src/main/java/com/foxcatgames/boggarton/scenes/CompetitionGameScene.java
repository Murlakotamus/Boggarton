package com.foxcatgames.boggarton.scenes;

import org.lwjgl.input.Keyboard;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.MultiplayerGame;
import com.foxcatgames.boggarton.game.utils.Victories;
import com.foxcatgames.boggarton.players.real.RealMultiplayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class CompetitionGameScene extends AbstractMultiplayerScene {

    MultiplayerGame games[] = new MultiplayerGame[2];

    public CompetitionGameScene(final int width, final int height, final int[] prognosis, final int figureSize, final YuckTypes yuckType,
            final RandomTypes randomType, final DifficultyTypes difficulty) {

        super(SceneItem.COMPETITION_PRACTICE, width, figureSize);

        for (int i = 0; i < PLAYERS; i++) {
            games[i] = new MultiplayerGame(layer, X + 446 * i, Y, width, height, prognosis[i], figureSize, difficulty.getSetSize(), Victories.getVictories(i),
                    yuckType, randomType, i == 0 ? Const.SOUNDS_LEFT : Const.SOUNDS_RIGHT);
            games[i].setName(PLAYERS_NAMES[i]);
        }

        for (int i = 0; i < PLAYERS; i++)
            games[i].startGame();

        leftPlayer = new RealMultiplayer(games[0], Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_S, Keyboard.KEY_W);
        rightPlayer = new RealMultiplayer(games[1], Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_UP);
    }

    @Override
    protected void hideGlass() {
        hideGlass(games);
    }

    @Override
    protected void showGlass() {
        showGlass(games);
    }

    @Override
    protected void changes() {
        changes(games);
    }

    @Override
    protected void start() {
    }
}
