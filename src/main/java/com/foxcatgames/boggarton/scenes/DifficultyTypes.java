package com.foxcatgames.boggarton.scenes;

import java.util.ArrayList;
import java.util.List;

public enum DifficultyTypes {
    EASY(4, "Easy"), NORMAL(5, "Normal"), HARD(6, "Hard"), IMPOSSIBLE(7, "Impossible");

    private int setSize;
    private String name;

    private DifficultyTypes(int setSize, String name) {
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
        List<String> list = new ArrayList<>();
        for (DifficultyTypes difficulty : DifficultyTypes.values())
            list.add(difficulty.getName());
        String result[] = new String[list.size()];
        return list.toArray(result);
    }

}
