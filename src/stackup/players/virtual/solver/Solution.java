package stackup.players.virtual.solver;

public class Solution {

    private int score;
    private int fullness;
    private String turns;

    public Solution(final String turns, final int score, final int fullness) {
        this.turns = turns;
        this.score = score;
        this.fullness = fullness;
    }

    public Solution() {
        this.turns = "";
        this.score = -1;
        this.fullness = 0;
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

    public int getPrice() {
        return getPrice(fullness, score);
    }

    public static int getPrice(final int fullness, final int score) {
        return fullness * fullness + score;
    }

    @Override
    public String toString() {
        return "Score: " + score + "\nMoves: " + turns;
    }
}
