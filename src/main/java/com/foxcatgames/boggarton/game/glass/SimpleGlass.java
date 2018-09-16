package com.foxcatgames.boggarton.game.glass;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;

public class SimpleGlass extends AbstractVisualGlass {

    public SimpleGlass(Layer layer, Vector2f position, int width, int height, final Map<String, Integer> sounds) {
        super(layer, position, width, height, sounds);
    }
}
