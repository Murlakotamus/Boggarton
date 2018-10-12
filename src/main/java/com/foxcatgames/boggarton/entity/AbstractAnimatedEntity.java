package com.foxcatgames.boggarton.entity;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Texture;

abstract public class AbstractAnimatedEntity extends AbstractEntity {

    protected Texture[] animationTextures;
    protected boolean displayAnimation = false;
    protected float animationCursor;
    protected boolean looped = false;
    protected float animationSpeed = 8.8f;

    @Override
    protected void initEntity() {
        animationTextures = Const.TEXTURE_LOADER.getAnimation(type);
        originalWidth = animationTextures[0].getTextureWidth();
        originalHeight = animationTextures[0].getTextureHeight();
    }

    public void startAnimation() {
        displayAnimation = true;
    }

    public void startLoopedAnimation() {
        looped = true;
        displayAnimation = true;
    }

    public boolean isAnimated() {
        return displayAnimation;
    }

    public void stopAnimation() {
        displayAnimation = false;
        animationCursor = 0;
    }

    public boolean isLooped() {
        return looped;
    }

    public void setLooped(final boolean looped) {
        this.looped = looped;
    }
}
