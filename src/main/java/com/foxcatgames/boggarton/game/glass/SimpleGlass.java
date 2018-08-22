package com.foxcatgames.boggarton.game.glass;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;

public class SimpleGlass extends AbstractSimpleGlass {

    public SimpleGlass(Layer layer, Vector2f position, int width, int height, int... sounds) {
        super(layer, position, width, height, sounds);
    }
}
