package com.foxcatgames.boggarton.game;

import java.util.concurrent.atomic.AtomicInteger;

public interface IBrick {

    static AtomicInteger generatedId = new AtomicInteger(0);

    public boolean isKill();
    public void setKill();
    public int getType();
    int getId();
}
