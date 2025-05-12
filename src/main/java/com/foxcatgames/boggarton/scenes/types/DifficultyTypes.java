package com.foxcatgames.boggarton.scenes.types;

import com.foxcatgames.boggarton.game.utils.Utils;

public enum DifficultyTypes implements IMenu<DifficultyTypes> {
    EASY(4, "Easy"), NORMAL(5, "Normal"), HARD(6, "Hard"), IMPOSSIBLE(7, "Impossible");

    private final int setSize;
    private final String name;

    DifficultyTypes(final int setSize, final String name) {
        this.setSize = setSize;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DifficultyTypes relative(final int relativePosition) {
        return Utils.relativeEnumValue(this, DifficultyTypes.class, relativePosition);
    }

    public int getSetSize() {
        return setSize;
    }

    public static String[] getNames() {
        return Utils.getNames(DifficultyTypes.class);
    }
}
