package stackup.entity;

import static stackup.Const.DEFAULT_Z;
import static stackup.Const.SCREEN_HEIGHT;
import static stackup.Const.SCREEN_WIDTH;
import static stackup.Const.TEXTURE_LOADER;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import stackup.Logger;
import stackup.engine.Layer;
import stackup.engine.Texture;
import stackup.scenes.AbstractScene;

public abstract class Entity {

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

    // kill an entity from the layer
    // killing make the reference invalid and it will be cleaned up by gc
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

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public boolean isSpawned() {
        return spawned;
    }

    @Override
    public String toString() {
        if (!(this instanceof Brick))
            return "";

        String result = "--------------------------\n";
        result += "px: " + position.x + "\n";
        result += "py: " + position.y + "\n";
        result += "width: " + originalWidth + "\n";
        result += "height: " + originalHeight + "\n";
        result += "spawned: " + spawned + "\n";

        return result;
    }
}
