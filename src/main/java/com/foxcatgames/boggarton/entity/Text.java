package com.foxcatgames.boggarton.entity;

import org.lwjgl.opengl.GL11;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;

public class Text extends AbstractAnimatedEntity {

    private char[] chars;

    public final void setString(final String string) {
        chars = string.toCharArray();
    }

    public Text(final String string, final int type, final Layer layer) {
        setString(string);
        this.type = type;
        this.layer = layer;

        initEntity();
        setRatio(1);
    }

    @Override
    public void draw() {
        GL11.glLoadIdentity();
        GL11.glTranslatef(position.x, -position.y, Const.DEFAULT_Z);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        for (int i = 0; i < chars.length; i++) {
            drawChar(i);
            GL11.glTranslatef(width, 0, 0);
        }
    }

    private void drawChar(final int i) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, animationTextures[chars[i]].getTextureId());
        coreDraw();
    }
}
