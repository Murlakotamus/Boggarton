package com.foxcatgames.boggarton.game.forecast;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.game.figure.MenuFigure;

public class MenuForecast extends AbstractVisualForecast {

    public MenuForecast(final Layer layer, final Vector2f startPos, final int prognosis, final int size, final int difficulty) {
        super(prognosis, size);
        frame = new Frame(layer, startPos, size, prognosis, true, true);

        for (int i = 0; i < prognosis; i++)
            figures[i] = new MenuFigure(layer, new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + i * BOX + BORDER), size, difficulty);
    }

    @Override
    public void setNext() {
    }
}
