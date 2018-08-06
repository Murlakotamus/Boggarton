package com.foxcatgames.boggarton.scenes.types;

import java.util.ArrayList;
import java.util.List;

public enum SoundTypes {
    ON, OFF;

    public SoundTypes next() {
        switch(this) {
        case ON:
            return OFF;
        case OFF:
        default:
            return ON;
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
