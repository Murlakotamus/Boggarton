package com.foxcatgames.boggarton.engine;

public interface ITextureLoader {

    void init();
    Texture getTexture(int textureID);
    Texture[] getAnimation(int animationID);
}
