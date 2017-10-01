package stackup.entity;

import stackup.Const;
import stackup.engine.Texture;

public abstract class AnimatedEntity extends Entity {

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
        this.displayAnimation = true;
    }

    public void startLoopedAnimation() {
        looped = true;
        this.displayAnimation = true;
    }

    public boolean isAnimated() {
        return displayAnimation;
    }

    public void stopAnimation() {
        this.displayAnimation = false;
        this.animationCursor = 0;
    }

    public boolean isLooped() {
        return looped;
    }

    public void setLooped(final boolean looped) {
        this.looped = looped;
    }
}
