package com.foxcatgames.boggarton.game.forecast;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;

public class SimpleForecast extends AbstractVisualForecast {

    final private int difficulty;

    public SimpleForecast(final Layer layer, final Vector2f startPos, final int prognosis, final int size, final int difficulty) {
        super(prognosis, size);
        this.difficulty = difficulty;
        frame = new Frame(layer, startPos, size, prognosis, false, true);

        for (int i = 0; i < prognosis; i++)
            figures[i] = new SimpleFigure(layer, new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + i * BOX + BORDER), size, difficulty);
    }

    @Override
    public void setNext() {
        for (int i = 0; i < prognosis - 1; i++) {
            figures[i] = figures[i + 1];
            ((SimpleFigure) figures[i]).shiftY(-BOX);
        }

        figures[prognosis - 1] = new SimpleFigure(frame.getLayer(),
                new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + (prognosis - 1) * BOX + BORDER), size, difficulty);
    }
}
