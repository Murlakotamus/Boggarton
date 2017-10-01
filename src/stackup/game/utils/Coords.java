package stackup.game.utils;

public class Coords {

    final public int i, j;

    public Coords(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        Coords obj = (Coords)o;
        return i == obj.i && j == obj.j;
    }

    @Override
    public int hashCode() {
        return 31 * i + j;
    }
}

