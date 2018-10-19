package com.foxcatgames.boggarton.players.virtual.solver;

public interface IEater {

    Solution chooseBest(Solution oldSolution, Solution newSolution);
    int getPrice(Solution solution);
    String getName();
}
