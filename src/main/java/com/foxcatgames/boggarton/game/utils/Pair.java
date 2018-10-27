package com.foxcatgames.boggarton.game.utils;

public class Pair<F, S> {
    private F first;
    private S second;

    protected Pair() {
        first = null;
        first = null;
    }

    public Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(final F first) {
        this.first = first;
    }

    public F getFirst() {
        return first;
    }

    public void setSecond(final S second) {
        this.second = second;
    }

    public S getSecond() {
        return second;
    }

    public static <O1, O2> Pair<O1, O2> create(final O1 first, final O2 second) {
        return new Pair<>(first, second);
    }

    public boolean isEmpty() {
        return first == null || second == null;
    }

    @Override
    public String toString() {
        return "first = " + (first == null ? "null" : first) + ", second = " + (second == null ? "null" : second);
    }
}
