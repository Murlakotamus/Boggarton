package com.foxcatgames.boggarton.scenes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.game.ReplayGame;
import com.foxcatgames.boggarton.players.virtual.MovesExecutor;

public class Replay extends AbstractOnePlayerGame {

    public Replay(final int width, final int height, final int forecast, final int lenght) {
        super(SceneItem.REPLAY_GAME);

        final StringBuilder moves = new StringBuilder();
        final List<String> events = new ArrayList<>();

        final String filename = this.getClass().getResource("/games/game.txt").getFile();
        try (final BufferedReader in = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            while ((line = in.readLine()) != null)
                if (line.startsWith(Const.FIGURE) || line.startsWith(Const.YUCK))
                    events.add(line);
                else if (Const.GAMEOVER.equals(line))
                    break;
                else
                    moves.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        game = new ReplayGame(layer, X, Y, width, height, 0, 3, events);
        game.setName("Replay");
        game.startGame();

        new MovesExecutor(game, moves.toString().toCharArray());
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
