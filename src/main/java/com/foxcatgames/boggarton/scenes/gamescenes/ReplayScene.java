package com.foxcatgames.boggarton.scenes.gamescenes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.ReplayGame;
import com.foxcatgames.boggarton.game.figure.PredefinedFigure;
import com.foxcatgames.boggarton.game.forecast.PredefinedForecast;
import com.foxcatgames.boggarton.game.glass.ReplayGlass;
import com.foxcatgames.boggarton.players.IPlayer;
import com.foxcatgames.boggarton.players.virtual.MovesExecutor;
import com.foxcatgames.boggarton.scenes.AbstractOnePlayerScene;
import com.foxcatgames.boggarton.scenes.SceneItem;

public class ReplayScene extends AbstractOnePlayerScene<Brick, PredefinedFigure, ReplayGlass, PredefinedForecast, ReplayGame> {

    final char[] moves;

    public ReplayScene(final int width, final int height) {
        super(SceneItem.REPLAY);

        final List<String> events = new ArrayList<>();
        final StringBuilder moves = new StringBuilder();
        final String filename = Objects.requireNonNull(this.getClass().getResource("/games/game.txt")).getFile();

        try (final BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = in.readLine()) != null)
                if (line.startsWith(Const.FIGURE_STR) || line.startsWith(Const.YUCK_STR) || line.startsWith(Const.SCORE_STR))
                    events.add(line);
                else if (Const.GAMEOVER_STR.equals(line))
                    break;
                else if (line.startsWith(Const.MOVES_STR))
                    moves.append(line.substring(line.indexOf(" ")).trim());
        } catch (final IOException e) {
            Logger.printStackTrace(e);
        }
        this.moves = moves.toString().toCharArray();

        int figureSize = 3;
        for (final String event : events)
            if (event.startsWith(Const.FIGURE_STR)) {
                figureSize = event.substring(Const.FIGURE_STR.length()).length();
                break;
            }

        game = new ReplayGame(layer, X, Y, width, height, figureSize, events, Const.SOUNDS);
        game.setName("Replay");
    }

    @Override
    protected void start() {
        game.startGame();
        new MovesExecutor(game, moves);
    }

    @Override
    protected void terminate() {
        super.terminate();
    }

    @Override
    protected void saveOutcome(final IPlayer player) {
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }
}
