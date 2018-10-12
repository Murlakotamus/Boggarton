package com.foxcatgames.boggarton.scenes.types;

import java.util.ArrayList;
import java.util.List;

public enum DifficultyTypes {
    EASY(4, "Easy"), NORMAL(5, "Normal"), HARD(6, "Hard"), IMPOSSIBLE(7, "Impossible");

    private final int setSize;
    private final String name;

    private DifficultyTypes(final int setSize, final String name) {
        this.setSize = setSize;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public DifficultyTypes next() {
        switch (this) {
        case NORMAL:
            return HARD;
        case HARD:
            return IMPOSSIBLE;
        case IMPOSSIBLE:
            return EASY;
        case EASY:
        default:
            return NORMAL;
        }
    }

    public int getSetSize() {
        return setSize;
    }

    public static String[] getAllDifficultyNames() {
        final List<String> list = new ArrayList<>();
        for (final DifficultyTypes difficulty : DifficultyTypes.values())
            list.add(difficulty.getName());
        final String result[] = new String[list.size()];
        return list.toArray(result);
    }

}
