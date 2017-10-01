package stackup.game;

import org.lwjgl.util.vector.Vector2f;

import static stackup.Const.BOX;
import static stackup.entity.Frame.BORDER;
import stackup.engine.Layer;
import stackup.entity.Brick;
//import stackup.entity.Frame;
import stackup.game.utils.Utils;

public class Figure extends AbstractFigure {

    private final Vector2f position;

    public Figure(final Layer layer, final Vector2f position, final int lenght, final int setSize) {
        super(lenght);
        this.position = position;
        number = lenght;

        for (int j = 0; j < lenght; j++)
            bricks[j] = new Brick(Utils.randomBrick(setSize), layer);

        respawn();
    }

    public void setPosition(final Vector2f position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
        respawn();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void shiftY(final int shift) {
        position.setY(position.getY() + shift);
        respawn();
    }

    @Override
    public void rotate() {
        super.rotate();
    }

    final public void respawn() {
        for (int i = 0; i < lenght; i++)
            if (bricks[i] != null) {
                final Brick brick = (Brick) bricks[i];
                brick.spawn(new Vector2f(position.getX() + i * BOX + BORDER, position.getY()));
            }
    }

    public void unspawn() {
        for (int i = 0; i < lenght; i++)
            if (bricks[i] != null)
                ((Brick) bricks[i]).unspawn();
    }
}
