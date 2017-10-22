package com.foxcatgames.boggarton.game.utils;

public class Changes {

    public Changes(boolean flag) {
        this.flag = flag;
    }

    private volatile boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
