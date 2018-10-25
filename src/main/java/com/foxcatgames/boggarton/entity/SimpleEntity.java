package com.foxcatgames.boggarton.entity;

import org.lwjgl.opengl.GL11;

import com.foxcatgames.boggarton.engine.Layer;

public class SimpleEntity extends AbstractEntity {

    public SimpleEntity(final int type, final Layer layer) {
        this.type = type;
        this.layer = layer;
        initEntity();
    }

    @Override
    public void draw() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.draw();
    }
}