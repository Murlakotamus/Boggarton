package com.foxcatgames.boggarton.scenes;

import com.foxcatgames.boggarton.scenes.types.DifficultyTypes;
import com.foxcatgames.boggarton.scenes.types.IMenu;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.SoundTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public enum MenuItem {

    START("Start"),
    MODE("Mode", SceneItem.getNames()),
    RANDOM_TYPE("Brick's appearance", RandomTypes.getNames()),
    DIFFICULTY("Difficulty", DifficultyTypes.getNames()),
    YUCKS("Yucks", YuckTypes.getNames()),
    FIGURE_SIZE("Figure size"),
    PROGNOSIS("Prognosis"),
    SOUND("Sound", SoundTypes.getNames()),
    ABOUT("About");

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
        if (position >= values.length)
            submenuElementPosition = 0;
        else
            submenuElementPosition = position;
    }

    public <E extends Enum<E>> void setSubmenuPosition(final E enumValue) {
        setSubmenuPosition(enumValue.ordinal());
    }

    public <E extends Enum<E> & IMenu<E>> void setNextPositionFor(final E enumValue) {
        final E newValue = SceneItem.nextEnumValue(enumValue);
        setSubmenuPosition(newValue);
    }
}
