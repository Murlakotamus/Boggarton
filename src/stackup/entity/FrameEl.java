package stackup.entity;

import org.lwjgl.opengl.GL11;

import stackup.engine.Layer;

public class FrameEl extends Entity {

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
