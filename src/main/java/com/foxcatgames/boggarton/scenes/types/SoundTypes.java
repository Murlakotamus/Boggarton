package com.foxcatgames.boggarton.scenes.types;

import com.foxcatgames.boggarton.game.utils.Utils;

public enum SoundTypes implements IMenu<SoundTypes> {
    ON("On"), OFF("Off");

    private final String name;

    SoundTypes(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SoundTypes relative(final int relativePosition) {
        return Utils.relativeEnumValue(this, SoundTypes.class, relativePosition);
    }

    public static String[] getNames() {
        return Utils.getNames(SoundTypes.class);
    }
}
