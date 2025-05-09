package com.foxcatgames.boggarton.game.forecast;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

abstract public class AbstractVisualForecast<B extends Brick, F extends AbstractVisualFigure<B>> extends AbstractForecast<B, F> {

    protected Frame frame;

    public AbstractVisualForecast(final F[] figures) {
        this(figures, 0, null);
    }

    public AbstractVisualForecast(final F[] figures, final int difficulties, final RandomTypes randomType) {
        super(figures, difficulties, randomType);
    }

    public void unspawn() {
        frame.unspawn();
        for (F figure: figures)
            figure.unspawn();
    }
}
