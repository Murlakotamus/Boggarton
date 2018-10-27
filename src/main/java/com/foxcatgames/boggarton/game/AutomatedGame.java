package com.foxcatgames.boggarton.game;

import com.foxcatgames.boggarton.game.utils.ICommand;

public interface AutomatedGame {

    void sendCommand(ICommand cmd) throws InterruptedException;
    void setSimpleGameOver(AutomatedGame game);
}
