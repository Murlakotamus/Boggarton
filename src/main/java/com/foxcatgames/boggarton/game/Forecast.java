package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Frame;

public class Forecast extends AbstractForecast {

    final private Frame frame;
    final private int difficulty;

    public Forecast(final Layer layer, final Vector2f startPos, final int prognosis, final int size,
            final int difficulty) {
        super(prognosis, size);
        this.difficulty = difficulty;
        frame = new Frame(layer, startPos, size, prognosis, false, true);

        for (int i = 0; i < prognosis; i++)
            figures[i] = new Figure(layer, new Vector2f(frame.getPosition().getX(),
                    frame.getPosition().getY() + i * BOX + BORDER), size, difficulty);
    }

    public Forecast(final Layer layer, final Vector2f startPos, final int prognosis, final int size,
            final int difficulty, final boolean forMenu) {
        super(prognosis, size);
        this.difficulty = difficulty;
        frame = new Frame(layer, startPos, size, prognosis, true, true);

        for (int i = 0; i < prognosis; i++)
            figures[i] = new Figure(layer, new Vector2f(frame.getPosition().getX(),
                    frame.getPosition().getY() + i * BOX + BORDER), size, difficulty, true);
    }

    @Override
    protected IFigure[] initFigures() {
        return new Figure[prognosis];
    }

    public void setNext() {
        for (int i = 0; i < prognosis - 1; i++) {
            figures[i] = figures[i + 1];
            ((Figure) figures[i]).shiftY(-BOX);
        }

        figures[prognosis - 1] = new Figure(frame.getLayer(),
                new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + (prognosis - 1) * BOX + BORDER),
                size, difficulty);
    }

    public void unspawn() {
        frame.unspawn();
        for (int i = 0; i < prognosis; i++)
            ((Figure) figures[i]).unspawn();
    }
}
