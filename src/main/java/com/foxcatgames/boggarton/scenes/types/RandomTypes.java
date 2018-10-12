package com.foxcatgames.boggarton.scenes.types;

import java.util.ArrayList;
import java.util.List;

public enum RandomTypes {
    RANDOM("Random", new int[] { 1, 1, 1, 1, 1, 1, 1 }), PROBABILISTIC("Probabilistic", new int[] { 64, 32, 16, 8, 4, 2, 1 }), MAD("Mad",
            new int[] { 500, 500, 500, 100, 50, 10, 1 });

    private final String name;
    private final int[] probabilities;

    private RandomTypes(final String name, final int[] probabilities) {
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
        final List<String> list = new ArrayList<>();
        for (final RandomTypes bricks : RandomTypes.values())
            list.add(bricks.getName());
        final String result[] = new String[list.size()];
        return list.toArray(result);
    }
}
