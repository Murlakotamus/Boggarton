package stackup.game;

import org.lwjgl.util.vector.Vector2f;

import static stackup.Const.BOX;
import static stackup.Const.BORDER;
import stackup.engine.Layer;
import stackup.entity.Frame;

public class Forecast extends AbstractForecast {

    final private Frame frame;
    final private int difficulty;

    public Forecast(final Layer layer, final Vector2f startPos, final int prognosis, final int size,
            final int difficulty) {
        super(prognosis, size);
        this.difficulty = difficulty;
        frame = new Frame(layer, startPos, size, prognosis);

        for (int i = 0; i < prognosis; i++)
            figures[i] = new Figure(layer, new Vector2f(frame.getPosition().getX(),
                    frame.getPosition().getY() + i * BOX + BORDER), size, difficulty);
    }

    public Forecast(final Layer layer, final Vector2f startPos, final int prognosis, final int size,
            final int difficulty, final boolean forMenu) {
        super(prognosis, size);
        this.difficulty = difficulty;
        frame = new Frame(layer, startPos, size, prognosis);

        for (int i = 0; i < prognosis; i++)
            figures[i] = new Figure(layer, new Vector2f(frame.getPosition().getX(),
                    frame.getPosition().getY() + i * BOX + BORDER), size, difficulty, true);
    }

    @Override
    protected IFigure[] initFigures() {
        return new Figure[prognosis];
    }

    public void setNext() {
        for (int i = prognosis - 1; i > 0; i--) {
            figures[i] = figures[i - 1];
            ((Figure) figures[i]).shiftY(BOX);
        }

        figures[0] = new Figure(frame.getLayer(),
                new Vector2f(frame.getPosition().getX(), frame.getPosition().getY() + BORDER),
                size, difficulty);
    }

    public void unspawn() {
        frame.unspawn();
        for (int i = 0; i < prognosis; i++)
            ((Figure) figures[i]).unspawn();
    }
}
