package com.foxcatgames.boggarton.scenes.types;

import com.foxcatgames.boggarton.game.utils.Utils;

public enum GameTypes implements IMenu<GameTypes> {
    HORIZONTAL("Horizontal"), VERTICAL("Vertical");

    private final String name;

    GameTypes(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GameTypes relative(final int relativePosition) {
        return Utils.relativeEnumValue(this, GameTypes.class, relativePosition);
    }

    public static String[] getNames() {
        return Utils.getNames(GameTypes.class);
    }
}
