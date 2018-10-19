package com.foxcatgames.boggarton.players.virtual.solver;

public class FullnessEater extends AbstractEater {

    @Override
    public int getPrice(final Solution solution) {
        return getPrice(solution.getFullness(), solution.getScore());
    }

    private int getPrice(final int fullness, final int score) {
        return fullness * fullness + score;
    }

    @Override
    public String getName() {
        return "fullness";
    }
}
