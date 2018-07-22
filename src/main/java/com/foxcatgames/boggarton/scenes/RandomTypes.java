package com.foxcatgames.boggarton.scenes;

import java.util.ArrayList;
import java.util.List;

public enum RandomTypes {
    RANDOM("Random", new int[] { 1, 1, 1, 1, 1, 1, 1 }), PROBABILISTIC("Probabilistic", new int[] { 64, 32, 16, 8, 4, 2, 1 }), MAD("Mad",
            new int[] { 500, 500, 500, 100, 50, 10, 1 });

    private String name;
    private int[] probabilities;

    private RandomTypes(String name, int[] probabilities) {
        this.name = name;
        this.probabilities = probabilities;
    }

    public String getName() {
        return name;
    }

    public int[] getRandomType() {
        return probabilities;
    }

    public RandomTypes next() {
        switch (this) {
        case PROBABILISTIC:
            return MAD;
        case MAD:
            return RANDOM;
        case RANDOM:
        default:
            return PROBABILISTIC;
        }
    }

    public static String[] getRandomTypeNames() {
        List<String> list = new ArrayList<>();
        for (RandomTypes bricks : RandomTypes.values())
            list.add(bricks.getName());
        String result[] = new String[list.size()];
        return list.toArray(result);
    }
}
