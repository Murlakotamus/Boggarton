package stackup.game;

import org.lwjgl.util.vector.Vector2f;

import stackup.Const;
import stackup.engine.Layer;
import stackup.entity.Frame;

public class Forecast extends AbstractForecast {

    final private Frame frame;
    final private int setSize;

    public Forecast(final Layer layer, final Vector2f startPos, final int debth, final int lenght,
            final int setSize) {
        super(debth, lenght);
        this.setSize = setSize;
        frame = new Frame(layer, startPos, lenght, debth);

        for (int i = 0; i < debth; i++)
            figures[i] = new Figure(layer, new Vector2f(frame.getPosition().getX(), frame
                    .getPosition().getY() + i * Const.BOX + Frame.BORDER), lenght, setSize);
    }

    @Override
    protected IFigure[] initFigures() {
        return new Figure[debth];
    }

    public void setNext() {
        for (int i = debth - 1; i > 0; i--) {
            figures[i] = figures[i - 1];
            ((Figure) figures[i]).shiftY(Const.BOX);
        }

        figures[0] = new Figure(frame.getLayer(), new Vector2f(frame.getPosition().getX(), frame
                .getPosition().getY() + Frame.BORDER), lenght, setSize);
    }
}
