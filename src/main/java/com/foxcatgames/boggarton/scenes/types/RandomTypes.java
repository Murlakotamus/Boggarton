package com.foxcatgames.boggarton.scenes.types;

import com.foxcatgames.boggarton.game.utils.Utils;

public enum RandomTypes implements IMenu<RandomTypes> {
    RANDOM("Random", new int[] { 1, 1, 1, 1, 1, 1, 1 }), PROBABILISTIC("Probabilistic", new int[] { 64, 32, 16, 8, 4, 2, 1 }), MAD("Mad",
            new int[] { 500, 500, 500, 100, 50, 10, 1 });

    private final String name;
    private final int[] probabilities;

    RandomTypes(final String name, final int[] probabilities) {
        this.name = name;
        this.probabilities = probabilities;
    }

    @Override
    public String getName() {
        return name;
    }

    public int[] getRandomType() {
        return probabilities;
    }

    @Override
    public RandomTypes relative(final int relativePosition) {
        return Utils.relativeEnumValue(this, RandomTypes.class, relativePosition);
    }

    public static String[] getNames() {
        return Utils.getNames(RandomTypes.class);
    }
}
