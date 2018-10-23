package com.foxcatgames.boggarton.game.utils;

public class Changes {

    private volatile boolean flag;

    public Changes(final boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(final boolean flag) {
        this.flag = flag;
    }
}
