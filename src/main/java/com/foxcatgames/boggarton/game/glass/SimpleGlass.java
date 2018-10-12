package com.foxcatgames.boggarton.game.glass;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.figure.SimpleFigure;

public class SimpleGlass extends AbstractVisualGlass<Brick, SimpleFigure> {

    public SimpleGlass(final Layer layer, final Vector2f position, final int width, final int height, final Map<String, Integer> sounds) {
        super(new Brick[width][height], layer, position, width, height, sounds);
    }
}
