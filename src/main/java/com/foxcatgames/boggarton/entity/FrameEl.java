package com.foxcatgames.boggarton.entity;

import org.lwjgl.opengl.GL11;

import com.foxcatgames.boggarton.engine.Layer;

public class FrameEl extends AbstractEntity {

    public FrameEl(int type, Layer layer) {
        this.type = type;
        this.layer = layer;
        initEntity();

        this.setRatio(1);
    }

    public void draw() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.draw();

    }
}
