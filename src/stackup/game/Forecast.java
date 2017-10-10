package stackup.game;

import org.lwjgl.util.vector.Vector2f;

import static stackup.Const.BOX;
import static stackup.Const.BORDER;
import stackup.engine.Layer;
import stackup.entity.Frame;

public class Forecast extends AbstractForecast {

    final private Frame frame;
    final private int difficulty;

    public Forecast(final Layer layer, final Vector2f startPos, final int debth, final int lenght,
            final int setSize) {
        super(debth, lenght);
        this.difficulty = setSize;
        frame = new Frame(layer, startPos, lenght, debth);

        for (int i = 0; i < debth; i++)
            figures[i] = new Figure(layer, new Vector2f(frame.getPosition().getX(),
                    frame.getPosition().getY() + i * BOX + BORDER), lenght, setSize);
    }

    public Forecast(final Layer layer, final Vector2f startPos, final int debth, final int lenght,
            final int setSize, final boolean forMenu) {
        super(debth, lenght);
        this.difficulty = setSize;
        frame = new Frame(layer, startPos, lenght, debth);

        for (int i = 0; i < debth; i++)
            figures[i] = new Figure(layer, new Vector2f(frame.getPosition().getX(),
                    frame.getPosition().getY() + i * BOX + BORDER), lenght, setSize, true);
    }

    @Override
    protected IFigure[] initFigures() {
        return new Figure[debth];
    }

    public void setNext() {
        for (int i = debth - 1; i > 0; i--) {
            figures[i] = figures[i - 1];
            ((Figure) figures[i]).shiftY(BOX);
        }

        figures[0] = new Figure(frame.getLayer(),
                new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + BORDER),
                lenght, difficulty);
    }

    public void unspawn() {
        frame.unspawn();
        for (int i = 0; i < debth; i++)
            ((Figure) figures[i]).unspawn();
    }
}
