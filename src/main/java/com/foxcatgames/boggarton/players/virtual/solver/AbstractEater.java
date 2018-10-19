package com.foxcatgames.boggarton.players.virtual.solver;

abstract public class AbstractEater implements IEater {

    @Override
    public Solution chooseBest(final Solution oldSolution, final Solution newSolution) {
        final int oldResult = getPrice(oldSolution);
        final int newResult = getPrice(newSolution);
        return oldResult < newResult ? newSolution : oldSolution;
    }
}
