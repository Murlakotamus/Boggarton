package com.foxcatgames.boggarton.scenes.types;

import java.util.ArrayList;
import java.util.List;

public enum YuckTypes {
    NONE("None"), RANDOM("Additional random line"), HARD("Additional hard line"), NASTY("Nasty bricks");

    private final String name;

    private YuckTypes(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public YuckTypes next() {
        switch(this) {
        case RANDOM:
            return HARD;
        case HARD:
            return NASTY;
        case NASTY:
            return NONE;
        case NONE:
        default:
            return RANDOM;
        }
    }
    
    public static String[] getAllYuckNames() {
        final List<String> list = new ArrayList<>();
        for (final YuckTypes yuck : YuckTypes.values())
            list.add(yuck.getName());
        final String result[] = new String[list.size()];
        return list.toArray(result);
    }
}
