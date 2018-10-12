package com.foxcatgames.boggarton.game.utils;

public class Changes {

    public Changes(final boolean flag) {
        this.flag = flag;
    }

    private volatile boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(final boolean flag) {
        this.flag = flag;
    }
}
