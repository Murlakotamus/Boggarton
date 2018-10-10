package com.foxcatgames.boggarton.players.virtual.solver;

class Vector {

    private boolean direction;
    private int space;

    public Vector(final boolean direction, final int space) {
        this.direction = direction;
        this.space = space;
    }

    public int getSpace() {
        return space;
    }

    public boolean getDirection() {
        return direction;
    }
}