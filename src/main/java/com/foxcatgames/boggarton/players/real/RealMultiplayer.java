package com.foxcatgames.boggarton.players.real;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.AbstractVisualGame;
import com.foxcatgames.boggarton.game.MultiplayerGame;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.glass.MultiplayerGlass;

public class RealMultiplayer extends AbstractRealPlayer<Brick, SimpleFigure, MultiplayerGlass, SimpleForecast> {

    public RealMultiplayer(final AbstractVisualGame<Brick, SimpleFigure, MultiplayerGlass, SimpleForecast> game, final int keyLeft, final int keyRight,
            final int keyDown, final int keyRotate) {
        super(game, keyLeft, keyRight, keyDown, keyRotate);
    }

    @Override
    public GameParams getGamesParams() {
        final GameParams.Builder builder = buildParams();
        builder.setYuckName(((MultiplayerGame) game).getYuckType().getName());

        return builder.build();
    }
}
