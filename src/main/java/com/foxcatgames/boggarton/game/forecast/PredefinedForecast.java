package com.foxcatgames.boggarton.game.forecast;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.FIGURE_STR;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.game.figure.PredefinedFigure;
import com.foxcatgames.boggarton.game.utils.Pair;

public class PredefinedForecast extends AbstractVisualForecast<Brick, PredefinedFigure> {

    private int forecastCounter = 0;
    private int figuresCounter = 0;

    final private List<String> predefinedFigures = new ArrayList<>();

    public PredefinedForecast(final Layer layer, final Vector2f startPos, final int prognosis, final int size, final List<String> events) {
        super(new PredefinedFigure[prognosis]);
        frame = new Frame(layer, startPos, size, prognosis, false, true);

        for (final String event : events)
            if (event.startsWith(FIGURE_STR))
                predefinedFigures.add(event.substring(FIGURE_STR.length()));

        for (int i = 0; i < prognosis; i++) {
            if (i < predefinedFigures.size())
                figures[i] = new PredefinedFigure(layer, new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + i * BOX + BORDER), size,
                        predefinedFigures.get(forecastCounter++));
            else
                figures[i] = new PredefinedFigure(layer, new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + i * BOX + BORDER));
        }
    }

    public void setNext(final List<Pair<Integer, Integer>> pairs) {
        final int newMax = predefinedFigures.size() - 1 - figuresCounter;
        int max = figures.length - 1;
        if (max > newMax)
            max = newMax;
        for (int i = 0; i < max; i++) {
            figures[i] = figures[i + 1];
            figures[i].shiftY(-BOX);
        }
        figuresCounter++;

        if (forecastCounter < predefinedFigures.size())
            figures[figures.length - 1] = new PredefinedFigure(frame.getLayer(),
                    new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + (figures.length - 1) * BOX + BORDER), getFigureSize(),
                    predefinedFigures.get(forecastCounter++));
        else
            figures[figures.length - 1] = new PredefinedFigure(frame.getLayer(),
                    new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + (figures.length - 1) * BOX + BORDER)); // empty figure
    }
}
