package com.foxcatgames.boggarton.entity;

import static com.foxcatgames.boggarton.Const.*;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;

public class Frame {

    private static final int MINIMAL_HEIGHT = 1;
    private static final int WIDTH_WITH_BORDER = BORDER + BOX;

    private final Vector2f position;
    private final Layer layer;
    private final SimpleEntity[] frameElements;

    public Frame(final Layer layer, final Vector2f position, final int width, final int height,
            boolean isGlass, boolean isForecast) {
        this.layer = layer;
        this.position = position;
        frameElements = new SimpleEntity[width * 2 + height * 2 ];

        if (width == 0 || height == 0)
            return;

        int elCount = 0;
        for (int i = 1; i < width - 1; i++) {
            frameElements[elCount] = new SimpleEntity(FRAME_UPPER, layer);
            frameElements[elCount++]
                    .spawn(new Vector2f(position.getX() + i * BOX + BORDER, position.getY()));

            frameElements[elCount] = new SimpleEntity(FRAME_LOWER, layer);
            frameElements[elCount++].spawn(new Vector2f(position.getX() + i * BOX + BORDER,
                    position.getY() + BORDER + (height - 1) * BOX));
        }

        for (int i = 1; i < height - 1; i++) {
            frameElements[elCount] = new SimpleEntity(FRAME_LEFT, layer);
            frameElements[elCount++]
                    .spawn(new Vector2f(position.getX(), position.getY() + BORDER + i * BOX));

            frameElements[elCount] = new SimpleEntity(FRAME_RIGHT, layer);
            frameElements[elCount++].spawn(new Vector2f(position.getX() + (width - 1) * BOX + BORDER,
                    position.getY() + BORDER + i * BOX));
        }

        if (height > MINIMAL_HEIGHT) {
            if (isGlass) {
                frameElements[elCount] = new SimpleEntity(FRAME_UPPER_RIGHT, layer);
                frameElements[elCount++].spawn(new Vector2f(
                        position.getX() + WIDTH_WITH_BORDER + (width - 2) * BOX, position.getY()));

                if (!isForecast) {
                    frameElements[elCount] = new SimpleEntity(FRAME_UPPER_BRIDGE, layer);
                    frameElements[elCount++]
                            .spawn(new Vector2f(position.getX() - BORDER * 3, position.getY()));

                    frameElements[elCount] = new SimpleEntity(FRAME_LOWER_BRIDGE, layer);
                    frameElements[elCount++].spawn(new Vector2f(position.getX() - BORDER * 2,
                            position.getY() + BOX + BORDER));
                }

            } else if (isForecast) {
                frameElements[elCount] = new SimpleEntity(FRAME_UPPER, layer);
                frameElements[elCount++].spawn(new Vector2f(
                        position.getX() + WIDTH_WITH_BORDER + (width - 2) * BOX, position.getY()));
            }

            if (isForecast) {
                frameElements[elCount] = new SimpleEntity(FRAME_UPPER_LEFT, layer);
                frameElements[elCount++].spawn(new Vector2f(position.getX(), position.getY()));
            } else if (isGlass) {
                frameElements[elCount] = new SimpleEntity(FRAME_UPPER, layer);
                frameElements[elCount++].spawn(new Vector2f(position.getX() + BORDER, position.getY()));
            }

            frameElements[elCount] = new SimpleEntity(FRAME_LOWER_RIGHT, layer);
            frameElements[elCount++]
                    .spawn(new Vector2f(position.getX() + WIDTH_WITH_BORDER + (width - 2) * BOX,
                            position.getY() + WIDTH_WITH_BORDER + (height - 2) * BOX));

            frameElements[elCount] = new SimpleEntity(FRAME_LOWER_LEFT, layer);
            frameElements[elCount++].spawn(new Vector2f(position.getX(),
                    position.getY() + WIDTH_WITH_BORDER + (height - 2) * BOX));
        } else {
            frameElements[elCount] = new SimpleEntity(FRAME_FULL_LEFT, layer);
            frameElements[elCount++].spawn(new Vector2f(position.getX(), position.getY()));

            if (isForecast && isGlass) {
                frameElements[elCount] = new SimpleEntity(FRAME_FULL_RIGHT, layer);
                frameElements[elCount++].spawn(new Vector2f(position.getX() + BORDER + (width - 1) * BOX, position.getY()));
            } else if (isForecast & !isGlass) {
                frameElements[elCount] = new SimpleEntity(FRAME_UPPER, layer);
                frameElements[elCount++].spawn(new Vector2f(position.getX() + BORDER + (width - 1) * BOX, position.getY()));

                frameElements[elCount] = new SimpleEntity(FRAME_LOWER, layer);
                frameElements[elCount++].spawn(new Vector2f(position.getX() + BORDER + (width - 1) * BOX, position.getY() + BORDER));

                frameElements[elCount] = new SimpleEntity(FRAME_UPPER_BRIDGE, layer);
                frameElements[elCount++].spawn(new Vector2f(position.getX() + BORDER + width * BOX, position.getY() + + BOX + BORDER));
            }
        }

    }

    public void unspawn() {
        for (SimpleEntity fe : frameElements)
            if (fe != null)
                fe.unspawn();
    }

    public Vector2f getPosition() {
        return position;
    }

    public Layer getLayer() {
        return layer;
    }
}
