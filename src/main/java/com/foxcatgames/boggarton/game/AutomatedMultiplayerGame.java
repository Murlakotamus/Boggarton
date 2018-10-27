package com.foxcatgames.boggarton.game;

import java.util.Map;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

final public class AutomatedMultiplayerGame extends MultiplayerGame implements IAutomatedGame {

    private final GameAutomation gameAutomation;

    public AutomatedMultiplayerGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final int victories, YuckTypes yuckType, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, width, height, prognosis, figureSize, setSize, victories, yuckType, randomType, sounds);
        gameAutomation = new GameAutomation(sounds);
    }

    @Override
    public void processStage() {
        if (gameAutomation.processStage(stage, turnFinished))
            super.processStage();
    }

    @Override
    public void setGameOver() {
        gameAutomation.setGameOver(this, gamestateBuffer);
    }

    @Override
    public void setSimpleGameOver(IAutomatedGame game) {
        if (game == this)
            super.setGameOver();
    }

    @Override
    public void sendCommand(final ICommand cmd) throws InterruptedException {
        gameAutomation.sendCommand(cmd);
    }
}
