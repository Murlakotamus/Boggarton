package stackup.entity;

import org.lwjgl.opengl.GL11;

import stackup.Const;
import stackup.engine.Layer;

public class Text extends AnimatedEntity {

    public static final int LEFT_TO_RIGHT = 0;
    public static final int RIGHT_TO_LEFT = 1;

    private int mode = LEFT_TO_RIGHT;
    private char[] chars;

    public final void setString(final String string) {
        chars = string.toCharArray();
    }

    public void setMode(final int mode) {
        this.mode = mode;
    }

    public Text(final String string, final int type, final Layer layer) {
        setString(string);
        this.type = type;
        this.layer = layer;

        initEntity();
        setRatio(1);
    }

    @Override
    public void draw() {
        GL11.glLoadIdentity();
        GL11.glTranslatef(position.x, -position.y, Const.DEFAULT_Z);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        if (mode == LEFT_TO_RIGHT) {
            for (int i = 0; i < chars.length; i++) {
                drawChar(i);
                GL11.glTranslatef(width, 0, 0);
            }
        } else if (mode == RIGHT_TO_LEFT) {
            for (int i = chars.length - 1; i >= 0; i--) {
                drawChar(i);
                GL11.glTranslatef(-width, 0, 0);
            }
        }
    }

    private void drawChar(final int i) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, animationTextures[chars[i]].getTextureId());
        coreDraw();
    }

}
