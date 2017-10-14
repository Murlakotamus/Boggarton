package stackup.entity;

import static stackup.Const.*;

import org.lwjgl.util.vector.Vector2f;

import stackup.engine.Layer;

public class Frame {

    private static final int MINIMAL_HEIGHT = 1;
    private static final int WIDTH_WITH_BORDER = BORDER + BOX;

    private final Vector2f position;
    private final Layer layer;
    private final FrameEl[] frameEl;

    public Frame(final Layer layer, final Vector2f position, final int width, final int height,
            boolean isGlass, boolean isForecast) {
        this.layer = layer;
        this.position = position;

        if (isGlass)
            frameEl = new FrameEl[width * 2 + height * 2 + 1];
        else
            frameEl = new FrameEl[width * 2 + height * 2 - 2];

        if (width == 0 || height == 0)
            return;

        int elCount = 0;
        for (int i = 1; i < width - 1; i++) {
            frameEl[elCount] = new FrameEl(FRAME_UPPER, layer);
            frameEl[elCount++]
                    .spawn(new Vector2f(position.getX() + i * BOX + BORDER, position.getY()));

            frameEl[elCount] = new FrameEl(FRAME_LOWER, layer);
            frameEl[elCount++].spawn(new Vector2f(position.getX() + i * BOX + BORDER,
                    position.getY() + BORDER + (height - 1) * BOX));
        }

        for (int i = 1; i < height - 1; i++) {
            frameEl[elCount] = new FrameEl(FRAME_LEFT, layer);
            frameEl[elCount++]
                    .spawn(new Vector2f(position.getX(), position.getY() + BORDER + i * BOX));

            frameEl[elCount] = new FrameEl(FRAME_RIGHT, layer);
            frameEl[elCount++].spawn(new Vector2f(position.getX() + (width - 1) * BOX + BORDER,
                    position.getY() + BORDER + i * BOX));
        }

        if (height > MINIMAL_HEIGHT) {
            if (isGlass) {
                frameEl[elCount] = new FrameEl(FRAME_UPPER_RIGHT, layer);
                frameEl[elCount++].spawn(new Vector2f(
                        position.getX() + WIDTH_WITH_BORDER + (width - 2) * BOX, position.getY()));

                if (!isForecast) {
                    frameEl[elCount] = new FrameEl(FRAME_BRIDGE, layer);
                    frameEl[elCount++].spawn(new Vector2f(position.getX() - BORDER * 3, position.getY()));
                    frameEl[elCount] = new FrameEl(FRAME_BRIDGE, layer);
                    frameEl[elCount++].spawn(new Vector2f(position.getX() - BORDER * 1, position.getY()));

                    frameEl[elCount] = new FrameEl(FRAME_BRIDGE, layer);
                    frameEl[elCount++].spawn(new Vector2f(position.getX() - BORDER * 2, position.getY() + BOX + BORDER));
                }

            } else if (isForecast) {
                frameEl[elCount] = new FrameEl(FRAME_UPPER, layer);
                frameEl[elCount++].spawn(new Vector2f(
                        position.getX() + WIDTH_WITH_BORDER + (width - 2) * BOX, position.getY()));
            }

            if (isForecast) {
                frameEl[elCount] = new FrameEl(FRAME_UPPER_LEFT, layer);
                frameEl[elCount++].spawn(new Vector2f(position.getX(), position.getY()));
            } else if (isGlass) {
                frameEl[elCount] = new FrameEl(FRAME_UPPER, layer);
                frameEl[elCount++].spawn(new Vector2f(position.getX() + BORDER, position.getY()));
            }

            frameEl[elCount] = new FrameEl(FRAME_LOWER_RIGHT, layer);
            frameEl[elCount++]
                    .spawn(new Vector2f(position.getX() + WIDTH_WITH_BORDER + (width - 2) * BOX,
                            position.getY() + WIDTH_WITH_BORDER + (height - 2) * BOX));

            frameEl[elCount] = new FrameEl(FRAME_LOWER_LEFT, layer);
            frameEl[elCount++].spawn(new Vector2f(position.getX(),
                    position.getY() + WIDTH_WITH_BORDER + (height - 2) * BOX));
        } else {
            frameEl[elCount] = new FrameEl(FRAME_FULL_LEFT, layer);
            frameEl[elCount++].spawn(new Vector2f(position.getX(), position.getY()));

            frameEl[elCount] = new FrameEl(FRAME_FULL_RIGHT, layer);
            frameEl[elCount++].spawn(new Vector2f(
                    position.getX() + WIDTH_WITH_BORDER + (width - 2) * BOX, position.getY()));
        }

    }

    public void unspawn() {
        for (FrameEl fe : frameEl)
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
