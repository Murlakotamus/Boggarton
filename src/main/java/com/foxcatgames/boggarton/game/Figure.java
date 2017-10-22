package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.utils.Utils;

public class Figure extends AbstractFigure {

    private final Vector2f position;

    public Figure(final Layer layer, final Vector2f position, final int size,
            final int difficulty) {
        super(size);
        this.position = position;
        number = size;

        for (int j = 0; j < size; j++)
            bricks[j] = new Brick(Utils.randomBrick(difficulty), layer);

        respawn();
    }

    public Figure(final Layer layer, final Vector2f position, final int size, final int difficulty,
            final boolean forMenu) {
        super(size);
        this.position = position;

        for (int j = difficulty, i = 0; j > difficulty - size; j--, i++) {
            int value = difficulty - i % difficulty;
            value = value + Const.CURRENT_SET * 10;
            bricks[i] = new Brick(value, layer);
        }

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
