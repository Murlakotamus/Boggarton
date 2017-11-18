package com.foxcatgames.boggarton.players.virtual.solver;

public class Price implements IPrice {

    @Override
    public int getPrice(final Solution solution) {
        return solution.getFullness() * solution.getFullness() + solution.getScore();
    }
}
