package com.foxcatgames.boggarton.players.virtual.solver;

public class Solution {

    private String turns;
    private int score;
    private int fullness;
    private int chainLenght;
    private boolean gameOver;

    public Solution(final String turns, final int score, final int fullness, final int chainLenght, final boolean gameOver) {
        this.turns = turns;
        this.score = score;
        this.fullness = fullness;
        this.chainLenght = chainLenght;
        this.gameOver = gameOver;
    }

    public Solution() {
        this.turns = "";
        this.score = -1;
        this.fullness = 0;
        this.chainLenght = 0;
        this.gameOver = false;
    }

    public char[] getMoves() {
        return turns.toCharArray();
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

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public String toString() {
        return "Score: " + score + "\nMoves: " + turns;
    }
}
