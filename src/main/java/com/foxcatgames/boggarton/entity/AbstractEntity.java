package com.foxcatgames.boggarton.entity;

import static com.foxcatgames.boggarton.Const.DEFAULT_Z;
import static com.foxcatgames.boggarton.Const.SCREEN_HEIGHT;
import static com.foxcatgames.boggarton.Const.SCREEN_WIDTH;
import static com.foxcatgames.boggarton.Const.TEXTURE_LOADER;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.engine.Texture;
import com.foxcatgames.boggarton.scenes.AbstractScene;

abstract public class AbstractEntity {

    public int originalWidth;
    public int originalHeight;

    private float ratio = 1f;
    private boolean spawned = false;
    public float width;
    public float height;

    protected float tick;
    protected Texture texture;
    public int type;

    protected float textureUp = 1;
    protected float textureDown = 0;
    protected float textureLeft = 0;
    protected float textureRight = 1;

    protected Layer layer = null;

    final public Vector2f position = new Vector2f();

    public void setRatio(final float newRatio) {
        ratio = newRatio;
        width = originalWidth * ratio;
        height = originalHeight * ratio;
    }

    protected void initEntity() {
        texture = TEXTURE_LOADER.getTexture(type);
        originalWidth = texture.getTextureWidth();
        originalHeight = texture.getTextureHeight();
        width = originalWidth * ratio;
        height = originalHeight * ratio;
    }

    public void spawn(final Vector2f position, final Layer layer) {
        this.position.x = position.x;
        this.position.y = position.y;
        this.layer = layer;
        if (!spawned)
            this.layer.add(this);

        spawned = true;
    }

    public void spawn(final Vector2f position) {
        spawn(position, layer);
    }

    public boolean unspawn() {
        if (layer == null)
            return false;

        layer.remove(this);
        spawned = false;
        return true;
    }

    public void draw() {
        GL11.glLoadIdentity();
        GL11.glTranslatef(position.x, -position.y, DEFAULT_Z);
        GL11.glRotatef(0, 0, 0, 1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureId());
        coreDraw();
    }

    public void coreDraw() {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(textureRight, textureUp); // Upper right
        GL11.glVertex2f(width - (SCREEN_WIDTH / 2), (SCREEN_HEIGHT / 2) - height);

        GL11.glTexCoord2f(textureLeft, textureUp); // Upper left
        GL11.glVertex2f(-(SCREEN_WIDTH / 2), (SCREEN_HEIGHT / 2) - height);

        GL11.glTexCoord2f(textureLeft, textureDown); // Lower left
        GL11.glVertex2f(-(SCREEN_WIDTH / 2), (SCREEN_HEIGHT / 2));

        GL11.glTexCoord2f(textureRight, textureDown); // Lower right
        GL11.glVertex2f(width - (SCREEN_WIDTH / 2), (SCREEN_HEIGHT / 2));

        GL11.glEnd();
    }

    public void update() {
        if (position.x > SCREEN_WIDTH || position.y > SCREEN_HEIGHT) {
            unspawn();

            if (Logger.IS_ACTIVE)
                Logger.log(this.getClass().getName() + " died");
            return;
        }
    }

    public void updateTick() {
        tick = AbstractScene.tick;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(final Layer layer) {
        this.layer = layer;
    }

    public boolean isSpawned() {
        return spawned;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("--------------------------\n");
        result.append("px: ").append(position.x).append("\n");
        result.append("py: ").append(position.y).append("\n");
        result.append("width: ").append(originalWidth).append("\n");
        result.append("height: ").append(originalHeight).append("\n");
        result.append("spawned: ").append(spawned).append("\n");

        return result.toString();
    }
}
