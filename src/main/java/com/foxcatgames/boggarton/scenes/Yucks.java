package com.foxcatgames.boggarton.scenes;

import java.util.ArrayList;
import java.util.List;

public enum Yucks {
    RANDOM("Random"), PROBABILISTIC("Probabilistic"), HARD("Hard");

    private String name;

    private Yucks(String name) {
        this.name = name;
    }
    
    String getName() {
        return name;
    }
    
    public Yucks next() {
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
        for (Yucks yuck : Yucks.values())
            list.add(yuck.getName());
        String result[] = new String[list.size()];
        return list.toArray(result);
    }

}
