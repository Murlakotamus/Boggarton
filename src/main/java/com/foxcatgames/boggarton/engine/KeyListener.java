package com.foxcatgames.boggarton.engine;

import org.lwjgl.input.Keyboard;

public class KeyListener {

    private int keyMonitored;
    private boolean keyMonitoredWasPressed;

    public void setKeyMonitored(final int keyMonitored) {
        this.keyMonitored = keyMonitored;
    }

    public void onKeyDown() {
    };

    public void keyPressed() {
    };

    public void onKeyUp() {
    };

    public void checkKey() {
        if (Keyboard.isKeyDown(keyMonitored))
            if (keyMonitoredWasPressed)
                keyPressed();
            else {
                keyMonitoredWasPressed = true;
                onKeyDown();
            }
        else if (keyMonitoredWasPressed) {
            onKeyUp();
            keyMonitoredWasPressed = false;
        }
    }
}
