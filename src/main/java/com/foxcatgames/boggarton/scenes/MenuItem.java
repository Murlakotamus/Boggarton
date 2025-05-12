package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.scenes.types.*;

public enum MenuItem {

    START("Start"),
    BOGGARTON_TYPE("Game type", GameTypes.getNames()),
    MODE("Mode", SceneItem.getNames()),
    RANDOM_TYPE("Brick's appearance", RandomTypes.getNames()),
    DIFFICULTY("Difficulty", DifficultyTypes.getNames()),
    YUCKS("Yucks", YuckTypes.getNames()),
    FIGURE_SIZE("Figure size"),
    PROGNOSIS("Prognosis"),
    SOUND("Sound", SoundTypes.getNames()),
    ABOUT("About"),
    QUIT("Quit");

    final private String name;
    final private String[] values;
    private int submenuElementPosition;

    MenuItem(final String name) {
        this.name = name;
        values = null;
        submenuElementPosition = -1;
    }

    MenuItem(final String name, final String[] values) {
        this.name = name;
        this.values = values;
        this.submenuElementPosition = 0;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }

    public int getSubmenuElementPosition() {
        return submenuElementPosition;
    }

    public void setSubmenuPosition(final int position) {
        assert values != null;
        if (position >= values.length) {
            submenuElementPosition = 0;
        } else if (position < 0) {
            submenuElementPosition = values.length - 1;
        } else
            submenuElementPosition = position;
    }

    public <E extends Enum<E>> void setSubmenuPosition(final E enumValue) {
        setSubmenuPosition(enumValue.ordinal());
    }

    public <E extends Enum<E> & IMenu<E>> void setRelativePosition(final E enumValue, final int position) {
        final E newValue = SceneItem.getRelativeEnumValue(enumValue, position);
        setSubmenuPosition(newValue);
    }

}
