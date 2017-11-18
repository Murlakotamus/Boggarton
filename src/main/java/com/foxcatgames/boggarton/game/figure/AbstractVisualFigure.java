package com.foxcatgames.boggarton.game.figure;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.entity.Brick;

abstract public class AbstractVisualFigure extends AbstractFigure {

    private final Vector2f position;

    public AbstractVisualFigure(final int lenght, final Vector2f position) {
        super(lenght);
        this.position = position;
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
        respawn();
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
