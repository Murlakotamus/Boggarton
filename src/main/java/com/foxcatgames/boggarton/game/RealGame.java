package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.game.utils.Utils;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

public class RealGame extends AbstractSimpleGame {

    private static final int FIGURES_FOR_NEXT_EMPTY_BRICK = 5;
    private int oldEmptyBrickLines;

    private final ICommand satisfyCondition = new ICommand() {
        @Override
        public void execute() {
            oldEmptyBrickLines++;
        }
    };

    public RealGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, prognosis, figureSize, setSize, randomType, sounds);
        glass = new SimpleGlass(layer, new Vector2f(x + figureSize * BOX + 20, y), width, height, sounds);
    }

    @Override
    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                nextFigure();
            } else {
                final int emptyBrickLines = getGlass().getCount() / FIGURES_FOR_NEXT_EMPTY_BRICK;
                if (emptyBrickLines <= oldEmptyBrickLines)
                    charge();
                else {
                    charge(getEmptyBricks(), satisfyCondition);
                }
            }
            break;
        default:
            super.processStage();
        }
    }

    private List<Pair<Integer, Integer>> getEmptyBricks() {
        final List<Pair<Integer, Integer>> list = new ArrayList<>();
        list.add(new Pair<>(Utils.random(getForecast().getFigureSize()), Const.EMPTY));
        return list;
    }
}
