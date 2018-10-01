package com.foxcatgames.boggarton.scenes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.ReplayGame;
import com.foxcatgames.boggarton.players.IPlayer;
import com.foxcatgames.boggarton.players.virtual.MovesExecutor;

public class ReplayScene extends AbstractOnePlayerScene {

    public ReplayScene(final int width, final int height, final int figureSize) {
        super(SceneItem.REPLAY);

        final StringBuilder moves = new StringBuilder();
        final List<String> events = new ArrayList<>();

        final String filename = this.getClass().getResource("/games/game.txt").getFile();
        try (final BufferedReader in = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            while ((line = in.readLine()) != null)
                if (line.startsWith(Const.FIGURE_STR) || line.startsWith(Const.YUCK_STR))
                    events.add(line);
                else if (Const.GAMEOVER_STR.equals(line))
                    break;
                else if (line.startsWith(Const.MOVES_STR))
                    moves.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        game = new ReplayGame(layer, X, Y, width, height, 0, 3, events, Const.SOUNDS);
        game.setName("Replay");
        game.startGame();

        new MovesExecutor(game, moves.toString().toCharArray());
    }

    @Override
    protected void saveOutcome(IPlayer player) {
    }

    @Override
    protected void hideGlass() {
    }

    @Override
    protected void showGlass() {
    }

    @Override
    protected void start() {
    }
}