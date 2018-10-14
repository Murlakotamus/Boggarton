package com.foxcatgames.boggarton.players.virtual.solver;

public class Solution {

    private String turns;
    private int score;
    private int fullness;
    private int chainLenght;

    public Solution(final String turns, final int score, final int fullness, final int chainLenght) {
        this.turns = turns;
        this.score = score;
        this.fullness = fullness;
        this.chainLenght = chainLenght;
    }

    public Solution() {
        this.turns = "";
        this.score = -1;
        this.fullness = 0;
        this.chainLenght = 0;
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

    public int getChainLenght() {
        return chainLenght;
    }

    @Override
    public String toString() {
        return "Score: " + score + "\nMoves: " + turns;
    }
}
