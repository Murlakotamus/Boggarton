package stackup.game.utils;

public class Pair<F, S> {
    private F first;
    private S second;

    protected Pair() {
    }

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public F getFirst() {
        return first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object src) {
        if (src instanceof Pair) {
            return equals((Pair<?, ?>) src);
        } else {
            return false;
        }
    }

    public static <O1, O2> Pair<O1, O2> create(O1 first, O2 second) {
        return new Pair<O1, O2>(first, second);
    }
    
    public boolean isEmpty() {
        return first == null || second == null; 
    }

    @Override
    public String toString() {
        return "first = " + (first == null ? "null" : first.toString()) + ", second = "
                + (second == null ? "null" : second.toString());
    }
}
