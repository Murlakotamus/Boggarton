package stackup.engine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import stackup.entity.Entity;

public class Layer {

    final public ArrayList<Entity> entities = new ArrayList<Entity>(300);

    public Layer() {
    }

    public void add(final Entity entity) {
        entities.add(entity);
    }

    public boolean remove(final Entity entity) {
        return entities.remove(entity);
    }

    public boolean contains(final Entity entity) {
        return entities.contains(entity);
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        for (final Entity entity : entities)
            entity.draw();
    }

    public void update() {
        for (final Entity entity : entities) {
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
        for (final Entity entity : entities)
            result += entity;
        return result;
    }

}
