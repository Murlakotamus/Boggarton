package com.foxcatgames.boggarton.game;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractForecast;
import com.foxcatgames.boggarton.game.glass.AbstractGlass;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.Pair;

public interface IAutomatedGame<B extends IBrick, F extends AbstractFigure<B>, G extends AbstractGlass<B, F>, P extends AbstractForecast<B, F>> {

    Pair<GlassState<B, F>, P> getBuffer() throws InterruptedException;
    G getGlass();
    P getForecast();
    String getName();
    String getOldGlassState();

    int getLastScore();
    boolean isYuckHappened();
    boolean isGameOn();
    boolean isGameOver();

    void sendCommand(ICommand cmd) throws InterruptedException;
    void setSimpleGameOver(IAutomatedGame<B, F, G, P> game);
    void changeHappens();
    void waitChanges() throws InterruptedException;
    void clearBuffer() throws InterruptedException;
    void fillBuffer();
    void restoreSpeed();
    boolean moveLeft();
    boolean moveRight();
    boolean rotateFigure();
    void dropFigure();
    void setMaxSpeed();
    void finishTurn();
    void dropYuckHappened();

    GameParams.Builder buildParams();
}
