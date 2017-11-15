package com.foxcatgames.boggarton.players.virtual.solver;

public class Price implements IPrice {

    @Override
    public int getPrice(final Solution solution) {
        if (solution.isGameOver())
            return -1;

        return solution.getFullness() * solution.getFullness() + solution.getScore();
    }
}
