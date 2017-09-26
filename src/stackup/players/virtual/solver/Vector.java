package stackup.players.virtual.solver;

import static stackup.Const.LENGHT;
import static stackup.Const.WIDTH;

public final class Vector {

    public static final Vector[] MOVES_TO_LEFT;
    public static final Vector[] MOVES_TO_RIGHT;

    static {
        final int size = WIDTH - LENGHT;
        MOVES_TO_LEFT = new Vector[size + 1];
        for (int i = 0; i <= size; i++)
            MOVES_TO_LEFT[i] = new Vector(false, i);

        MOVES_TO_RIGHT = new Vector[size];
        for (int i = 0; i < size; i++)
            MOVES_TO_RIGHT[i] = new Vector(true, i + 1);
    }

    private final boolean direction;
    private final int space;

    private Vector(final boolean direction, final int space) {
        this.direction = direction;
        this.space = space;
    }

    public int getSpace() {
        return space;
    }

    public boolean getDirection() {
        return direction;
    }
}
