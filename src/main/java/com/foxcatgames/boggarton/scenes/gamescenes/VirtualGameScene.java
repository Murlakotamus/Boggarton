package com.foxcatgames.boggarton.scenes.gamescenes;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.VirtualGame;
import com.foxcatgames.boggarton.game.utils.DbHandler;
import com.foxcatgames.boggarton.players.IPlayer;
import com.foxcatgames.boggarton.players.virtual.VirtualAdaptivePlayer;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class VirtualGameScene {

    private IPlayer player;
    private final VirtualGame game;

    public VirtualGameScene(final int width, final int height, final int prognosis, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        game = new VirtualGame(width, height, prognosis, figureSize, difficulty.getSetSize(), randomType);
        game.setName("Virtual");
    }

    protected void saveOutcome() {
        DbHandler.saveOutcome(player);
    }

    protected void start() {
        game.initLogger();
        game.startGame();
        player = new VirtualAdaptivePlayer<>(game, 2, Const.FULLNESS_EATER);
    }

    protected void terminate() {
        game.closeLogger();
    }
}
