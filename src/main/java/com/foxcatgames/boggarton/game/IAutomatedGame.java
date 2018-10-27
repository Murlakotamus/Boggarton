package com.foxcatgames.boggarton.game;

import com.foxcatgames.boggarton.game.utils.ICommand;

public interface IAutomatedGame {

    void sendCommand(ICommand cmd) throws InterruptedException;
    void setSimpleGameOver(IAutomatedGame game);
}
