package stackup.entity;

import org.lwjgl.opengl.GL11;

import stackup.engine.Layer;

public class SimpleEntity extends Entity {

    public SimpleEntity(final int type, final Layer layer) {
        this.type = type;
        this.layer = layer;
        initEntity();
    }

    public void draw() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.draw();

    }
}