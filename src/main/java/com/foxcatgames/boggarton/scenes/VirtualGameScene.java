package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.game.VirtualGame;
import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class VirtualGameScene {

    //private IPlayer player;

    public VirtualGameScene(final int width, final int height, final int prognosis, final int figureSize, final RandomTypes randomType,
            final DifficultyTypes difficulty) {

        VirtualGame game = new VirtualGame(width, height, prognosis, figureSize, difficulty.getSetSize(), randomType);
        game.setName("Virtual");
        game.initLogger();
        game.startGame();
        //player = new VirtualAdaptivePlayer(game, new Price());
    }

    protected void saveOutcome() {
        //DbHandler.saveOutcome(player);
    }

}
