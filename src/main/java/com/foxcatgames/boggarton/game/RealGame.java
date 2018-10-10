package com.foxcatgames.boggarton.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.game.utils.Utils;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class RealGame extends AbstractOnePlayerGame {

    private static final int FIGURES_FOR_NEXT_EMPTY_BRICK = 5;

    private int oldEmptyBrickLines = 0;
    private final ICommand satisfyCondition = new ICommand() {

        @Override
        public void execute() {
            oldEmptyBrickLines++;
        }
    };

    public RealGame(final Layer layer, final int x, final int y, final int width, final int height, final int forecast, final int figureSize, final int setSize,
            final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, width, height, forecast, figureSize, setSize, randomType, sounds);
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                logFigure(nextFigure());
                logMoves();
            } else {
                int emptyBrickLines = getGlass().getCount() / FIGURES_FOR_NEXT_EMPTY_BRICK;
                if (emptyBrickLines <= oldEmptyBrickLines)
                    charge();
                else {
                    charge(getPairs(), satisfyCondition);
                }
            }
            break;
        default:
            super.processStage();
        }
    }

    private List<Pair<Integer, Integer>> getPairs() {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        list.add(new Pair<>(Utils.random(getForecast().getFigureSize()), Const.EMPTY));
        return list;
    }
}
