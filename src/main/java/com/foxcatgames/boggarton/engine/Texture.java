package com.foxcatgames.boggarton.engine;

public class Texture {
    int textureId;
    int textureHeight;
    int textureWidth;

    public Texture() {
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public void setTextureHeight(final int textureHeight) {
        this.textureHeight = textureHeight;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(final int textureId) {
        this.textureId = textureId;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public void setTextureWidth(final int textureWidth) {
        this.textureWidth = textureWidth;
    }
}
