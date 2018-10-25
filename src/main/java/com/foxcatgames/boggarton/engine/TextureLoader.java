package com.foxcatgames.boggarton.engine;

import static com.foxcatgames.boggarton.Const.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.foxcatgames.boggarton.Logger;

public class TextureLoader {

    private static final Texture[] TEXTURE = new Texture[1024];
    private static final Texture[][] ANIMATION = new Texture[1024][1024];
    private static final Map<String, BufferedImage> IMAGE_CASHE = new ConcurrentHashMap<>();

    public void init() {
        // Common elements
        TEXTURE[ABOUT] = loadTexture("/data/About.png", 0, 0, 151, 129);
        TEXTURE[GAME_OVER] = loadTexture("/data/GameOver.png", 0, 0, 180, 90);
        TEXTURE[LOSER] = loadTexture("/data/Loser.png", 0, 0, 180, 90);
        TEXTURE[TITLE] = loadTexture("/data/Title.png", 0, 0, 560, TITLE_HEIGHT);
        TEXTURE[WINNER] = loadTexture("/data/Winner.png", 0, 0, 180, 90);
        TEXTURE[GAME_PAUSED] = loadTexture("/data/GamePaused.png", 0, 0, 180, 90);

        // Frame elements
        TEXTURE[FRAME_UPPER] = loadTexture("/data/framedetails/1000.png", 0, 0, 30, 35);
        TEXTURE[FRAME_RIGHT] = loadTexture("/data/framedetails/0100.png", 0, 0, 35, 30);
        TEXTURE[FRAME_LOWER] = loadTexture("/data/framedetails/0010.png", 0, 0, 30, 35);
        TEXTURE[FRAME_LEFT] = loadTexture("/data/framedetails/0001.png", 0, 0, 35, 30);
        TEXTURE[FRAME_UPPER_RIGHT] = loadTexture("/data/framedetails/1100.png", 0, 0, 35, 35);
        TEXTURE[FRAME_LOWER_RIGHT] = loadTexture("/data/framedetails/0110.png", 0, 0, 35, 35);
        TEXTURE[FRAME_LOWER_LEFT] = loadTexture("/data/framedetails/0011.png", 0, 0, 35, 35);
        TEXTURE[FRAME_UPPER_LEFT] = loadTexture("/data/framedetails/1001.png", 0, 0, 35, 35);
        TEXTURE[FRAME_FULL_LEFT] = loadTexture("/data/framedetails/1011.png", 0, 0, 35, 40);
        TEXTURE[FRAME_FULL_RIGHT] = loadTexture("/data/framedetails/1110.png", 0, 0, 35, 40);
        TEXTURE[FRAME_UPPER_BRIDGE] = loadTexture("/data/framedetails/upper_bridge.png", 0, 0, 20, 5);
        TEXTURE[FRAME_LOWER_BRIDGE] = loadTexture("/data/framedetails/lower_bridge.png", 0, 0, 15, 5);

        // Bricks
        ANIMATION[A] = loadAnimation("/data/bricks/A.png", 6, 1, 30, 30);
        ANIMATION[B] = loadAnimation("/data/bricks/B.png", 6, 1, 30, 30);
        ANIMATION[C] = loadAnimation("/data/bricks/C.png", 6, 1, 30, 30);
        ANIMATION[D] = loadAnimation("/data/bricks/D.png", 6, 1, 30, 30);
        ANIMATION[E] = loadAnimation("/data/bricks/E.png", 6, 1, 30, 30);
        ANIMATION[F] = loadAnimation("/data/bricks/F.png", 6, 1, 30, 30);
        ANIMATION[G] = loadAnimation("/data/bricks/G.png", 6, 1, 30, 30);
        ANIMATION[EMPTY] = loadAnimation("/data/bricks/empty.png", 1, 1, 30, 30);

        // Fonts
        ANIMATION[DARK_FONT] = loadAnimation("/data/DarkFont.png", 16, 16, FONT_WIDTH, FONT_HEIGHT);
        ANIMATION[LIGHT_FONT] = loadAnimation("/data/LightFont.png", 16, 16, FONT_WIDTH, FONT_HEIGHT);
    }

    public Texture getTexture(final int textureID) {
        return TEXTURE[textureID];
    }

    public Texture[] getAnimation(final int animationID) {
        return ANIMATION[animationID];
    }

    private static void convertFromARGBToBGRA(final byte[] data) {
        for (int i = 0; i < data.length; i += 4) {
            data[i] ^= data[i + 3];
            data[i + 3] ^= data[i];
            data[i] ^= data[i + 3];

            data[i + 1] ^= data[i + 2];
            data[i + 2] ^= data[i + 1];
            data[i + 1] ^= data[i + 2];
        }
    }

    private static void convertFromBGRToRGB(final byte[] data) {
        for (int i = 0; i < data.length; i += 3) {
            // A small optimisation
            data[i] ^= data[i + 2];
            data[i + 2] ^= data[i];
            data[i] ^= data[i + 2];
        }
    }

    private BufferedImage getBufferedImage(final String path) {
        try {
            if (IMAGE_CASHE.get(path) == null) {
                final URL url = this.getClass().getResource(path);
                final BufferedImage buffImage = ImageIO.read(url);
                final byte[] data = ((DataBufferByte) buffImage.getRaster().getDataBuffer()).getData();

                switch (buffImage.getType()) {
                case BufferedImage.TYPE_4BYTE_ABGR:
                    convertFromARGBToBGRA(data);
                    break;
                case BufferedImage.TYPE_3BYTE_BGR:
                    convertFromBGRToRGB(data);
                    break;
                default:
                    Logger.log("Unknown type:" + buffImage.getType());
                    break;
                }
                IMAGE_CASHE.put(path, buffImage);
                return buffImage;
            }
            return IMAGE_CASHE.get(path);
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Texture loadTexture(final String path, final int xOffSet, final int yOffSet, final int textWidth, final int textHeight) {

        final BufferedImage buffImage = getBufferedImage(path);

        final int bytesPerPixel = buffImage.getColorModel().getPixelSize() / 8;
        final ByteBuffer scratch = ByteBuffer.allocateDirect(textWidth * textHeight * bytesPerPixel).order(ByteOrder.BIG_ENDIAN);
        final DataBufferByte data = ((DataBufferByte) buffImage.getRaster().getDataBuffer());

        for (int i = 0; i < textHeight; i++)
            scratch.put(data.getData(), (xOffSet + (yOffSet + i) * buffImage.getWidth()) * bytesPerPixel, textWidth * bytesPerPixel);
        scratch.rewind();

        final IntBuffer buf = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL11.glGenTextures(buf);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0));
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textWidth, textHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, scratch);

        final Texture newTexture = new Texture();
        newTexture.textureId = buf.get(0);
        newTexture.textureHeight = textHeight;
        newTexture.textureWidth = textWidth;

        return newTexture;
    }

    private Texture[] loadAnimation(final String path, final int cols, final int rows, final int textWidth, final int textHeight) {
        return loadAnimation(path, cols, rows, textWidth, textHeight, 0, 0);
    }

    private Texture[] loadAnimation(final String path, final int cols, final int rows, final int textWidth, final int textHeight, final int xOffSet,
            final int yOffSet) {

        final Texture[] toReturntextures = new Texture[cols * rows];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                toReturntextures[i * cols + j] = loadTexture(path, j * textWidth + xOffSet, i * textHeight + yOffSet, textWidth, textHeight);

        return toReturntextures;
    }
}
