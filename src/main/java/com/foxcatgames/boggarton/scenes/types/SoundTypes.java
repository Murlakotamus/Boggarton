package com.foxcatgames.boggarton.scenes.types;

import com.foxcatgames.boggarton.game.utils.Utils;

public enum SoundTypes implements IMenu<SoundTypes> {
    ON("On"), OFF("Off");

    private final String name;

    private SoundTypes(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SoundTypes next() {
        return Utils.nextEnumValue(this, SoundTypes.class);
    }

    public static String[] getNames() {
        return Utils.getNames(SoundTypes.class);
    }
}
