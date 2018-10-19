package com.foxcatgames.boggarton.players.virtual.solver;

public class ReactionsEater extends AbstractEater {

    @Override
    public int getPrice(final Solution solution) {
        return getPrice(solution.getReactions(), solution.getScore());
    }

    private int getPrice(final int reactions, final int score) {
        return reactions * reactions + score;
    }

    @Override
    public String getName() {
        return "reactions";
    }
}
