package com.foxcatgames.boggarton.game;

import java.util.concurrent.atomic.AtomicInteger;

public interface IBrick {

    static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    boolean isKill();
    void setKill();
    int getType();
    int getId();
}
