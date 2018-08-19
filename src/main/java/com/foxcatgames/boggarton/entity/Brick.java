package com.foxcatgames.boggarton.entity;

import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.opengl.GL11;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.IBrick;

public class Brick extends AbstractAnimatedEntity implements IBrick {

    private static AtomicInteger generatedId = new AtomicInteger(0);

    private boolean kill = false;
    private boolean crashing = false;
    private int id;

    public Brick(final int type, Layer layer) {
        this.type = type;
        this.layer = layer;
        id = generatedId.incrementAndGet();
        initEntity();
        stopAnimation();
        setRatio(1);
    }

    @Override
    public boolean isKill() {
        return kill;
    }

    @Override
    public void setKill() {
        kill = true;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void draw() {
        if (displayAnimation) {
            animationCursor += animationSpeed * tick;
            if (looped)
                animationCursor %= animationTextures.length;
            else if (animationCursor >= animationTextures.length)
                animationCursor = animationTextures.length - 1;
        } else
            animationCursor = 0;

        GL11.glLoadIdentity();
        GL11.glTranslatef(position.x, -position.y, Const.DEFAULT_Z);
        GL11.glRotatef(0, 0, 0, 1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, animationTextures[(int) animationCursor].getTextureId());
        coreDraw();
    }

    @Override
    public String toString() {
        String result = super.toString();
        if ("".equals(result))
            return result;

        result += "type: " + type + "\n";
        result += "kill: " + kill + "\n";
        return result;
    }

    public void setCrashing(boolean crashing) {
        this.crashing = crashing;
    }

    public boolean isCrashing() {
        return crashing;
    }

    public int getId() {
        return id;
    }
}
