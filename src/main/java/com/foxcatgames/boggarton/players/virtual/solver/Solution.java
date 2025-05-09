package com.foxcatgames.boggarton.players.virtual.solver;

public class Solution {

    private final String turns;
    private final int score;
    private final int fullness;

    public Solution(final StringBuilder turns, final int score, final int fullness) {
        this.turns = turns.toString();
        this.score = score;
        this.fullness = fullness;
    }

    public Solution(final int score) {
        this.turns = "";
        this.score = score;
        this.fullness = 0; // full, the worst state
    }

    public String getMoves() {
        return turns;
    }

    public int getScore() {
        return score;
    }

    public int getFullness() {
        return fullness;
    }

    @Override
    public String toString() {
        return "Score: " + score + "\nMoves: " + turns;
    }
}
