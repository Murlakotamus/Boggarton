package com.foxcatgames.boggarton.scenes;

import java.util.ArrayList;
import java.util.List;

public enum YuckTypes {
    RANDOM("Random"), PROBABILISTIC("Probabilistic"), HARD("Hard");

    private String name;

    private YuckTypes(String name) {
        this.name = name;
    }
    
    String getName() {
        return name;
    }
    
    public YuckTypes next() {
        switch(this) {
        case PROBABILISTIC:
            return HARD;
        case HARD:
            return RANDOM;
        case RANDOM:
        default:
            return PROBABILISTIC;
        }
    }
    
    public static String[] getAllYuckNames() {
        List<String> list = new ArrayList<>();
        for (YuckTypes yuck : YuckTypes.values())
            list.add(yuck.getName());
        String result[] = new String[list.size()];
        return list.toArray(result);
    }

}
