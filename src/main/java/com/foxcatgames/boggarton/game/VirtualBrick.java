package com.foxcatgames.boggarton.game;

public class VirtualBrick implements IBrick {

    private final int type;
    private boolean kill;
    private int id;

    public VirtualBrick(final int type) {
        this.type = type;
    }

    public VirtualBrick(final int type, final boolean withId) {
        this(type);
        if (withId)
            id = ID_GENERATOR.incrementAndGet();
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
