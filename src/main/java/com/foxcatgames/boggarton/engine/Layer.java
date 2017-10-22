package com.foxcatgames.boggarton.engine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.foxcatgames.boggarton.entity.AbstractEntity;

public class Layer {

    final public ArrayList<AbstractEntity> entities = new ArrayList<AbstractEntity>(300);

    public Layer() {
    }

    public void add(final AbstractEntity entity) {
        entities.add(entity);
    }

    public boolean remove(final AbstractEntity entity) {
        return entities.remove(entity);
    }

    public boolean contains(final AbstractEntity entity) {
        return entities.contains(entity);
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
        String result = "";
        for (final AbstractEntity entity : entities)
            result += entity;
        return result;
    }

}
