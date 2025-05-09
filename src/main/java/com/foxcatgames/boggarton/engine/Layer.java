package com.foxcatgames.boggarton.engine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.foxcatgames.boggarton.entity.AbstractEntity;

public class Layer {

    public final ArrayList<AbstractEntity> entities = new ArrayList<>(300);

    public Layer() {
    }

    public void add(final AbstractEntity entity) {
        entities.add(entity);
    }

    public void remove(final AbstractEntity entity) {
        entities.remove(entity);
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        for (final AbstractEntity entity : entities)
            entity.draw();
    }

    public void update() {
        for (final AbstractEntity entity : entities) {
            entity.updateTick();
            entity.update();
        }
    }

    public void removeAll() {
        entities.clear();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (final AbstractEntity entity : entities)
            result.append(entity);
        return result.toString();
    }
}
