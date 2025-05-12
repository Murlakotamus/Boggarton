package com.foxcatgames.boggarton.scenes.types;

import com.foxcatgames.boggarton.game.utils.Utils;

public enum YuckTypes implements IMenu<YuckTypes> {
    NONE("None"), RANDOM("Random line"), HARD("Hard line"), NASTY("Nasty brick");

    private final String name;

    YuckTypes(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public YuckTypes relative(final int relativePosition) {
        return Utils.relativeEnumValue(this, YuckTypes.class, relativePosition);
    }

    public static String[] getNames() {
        return Utils.getNames(YuckTypes.class);
    }
}
