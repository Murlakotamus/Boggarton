package com.foxcatgames.boggarton.game;

import java.util.concurrent.atomic.AtomicInteger;

public class VirtualBrick implements IBrick {

    private static AtomicInteger generatedId = new AtomicInteger(0);

    private final int type;
    private boolean kill = false;
    private int id;

    public VirtualBrick(final int type) {
        this.type = type;
    }

    public VirtualBrick(final int type, final boolean withId) {
        this(type);
        if (withId)
            id = generatedId.incrementAndGet();
    }

    @Override
    public boolean isKill() {
        return kill;
    }

    @Override
    public void setKill() {
        kill = true;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }
}
