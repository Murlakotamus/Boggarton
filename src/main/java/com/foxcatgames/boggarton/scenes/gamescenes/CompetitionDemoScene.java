package com.foxcatgames.boggarton.scenes.gamescenes;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.AutomatedMultiplayerGame;
import com.foxcatgames.boggarton.game.utils.Victories;
import com.foxcatgames.boggarton.players.virtual.VirtualEffectiveNonAdaptivePlayer;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;
import com.foxcatgames.boggarton.scenes.AbstractMultiplayerScene;
import com.foxcatgames.boggarton.scenes.SceneItem;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class CompetitionDemoScene extends AbstractMultiplayerScene {

    private static final long GAMEOVER_PAUSE = 3000;
    private final int[] prognosis;
    protected final AutomatedMultiplayerGame[] games = new AutomatedMultiplayerGame[PLAYERS];

    private long pauseBetweenGames;

    public CompetitionDemoScene(final int width, final int height, final int[] prognosis, final int figureSize, final YuckTypes yuckType,
            final RandomTypes randomType, final DifficultyTypes difficulty) {
        super(SceneItem.COMPETITION_DEMO, width, figureSize);
        this.prognosis = prognosis;
        for (int i = 0; i < PLAYERS; i++) {
            games[i] = new AutomatedMultiplayerGame(layer, X + 446 * i, Y, width, height, prognosis[i], figureSize, difficulty.getSetSize(),
                    Victories.getVictories(i), yuckType, randomType, i == 0 ? Const.SOUNDS_LEFT : Const.SOUNDS_RIGHT);
            games[i].setName(PLAYERS_NAMES[i]);
        }
    }

    @Override
    protected void start() {
        for (int i = 0; i < PLAYERS; i++) {
            games[i].initLogger();
            games[i].startGame();
        }
        leftPlayer = new VirtualEffectiveNonAdaptivePlayer<>(games[0], prognosis[0], Const.FULLNESS_EATER);
        rightPlayer = new VirtualAdaptivePlayer<>(games[1], prognosis[1], Const.FULLNESS_EATER);
    }

    @Override
    protected void terminate() {
        for (int i = 0; i < PLAYERS; i++)
            games[i].closeLogger();

        super.terminate();
        if (!escapePressed)
            nextScene(SceneItem.COMPETITION_DEMO);
    }

    private void checkAuto() {
        if (gameOver && pauseBetweenGames == 0)
            pauseBetweenGames = System.currentTimeMillis();

        if (gameOver && System.currentTimeMillis() - pauseBetweenGames > GAMEOVER_PAUSE)
            nextScene(SceneItem.MENU);
    }

    @Override
    protected void setGameOver() {
        super.setGameOver(games);
    }

    @Override
    protected void changes() {
        checkAuto();
        changes(games);
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }
}
