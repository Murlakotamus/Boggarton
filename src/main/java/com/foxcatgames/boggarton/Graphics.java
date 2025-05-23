package com.foxcatgames.boggarton;

import static com.foxcatgames.boggarton.Const.SCREEN_HEIGHT;
import static com.foxcatgames.boggarton.Const.SCREEN_WIDTH;
import static com.foxcatgames.boggarton.Const.TEXTURE_LOADER;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.apple.eawt.Application;

public class Graphics {

    private static final boolean FULL_SCREEN = false;
    private static DisplayMode actualDisplayMode;

    public static void init() {
        initEngine();
        initGL();
    }

    public static void destroy() {
        Display.destroy();
    }

    private static void createOffScreenBuffer() {
        final int bytesPerPixel = 3;
        final ByteBuffer scratch = ByteBuffer.allocateDirect(1024 * 1024 * bytesPerPixel);
        final IntBuffer buf = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL11.glGenTextures(buf);
        GL11.glBindTexture(GL_TEXTURE_2D, buf.get(0));

        final int glType = GL_RGB;
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, glType, 1024, 1024, 0, glType, GL_UNSIGNED_BYTE, scratch);
    }

    private static ByteBuffer convertImageData(final BufferedImage bufferedImage) {
        final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false,
                Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

        final WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, bufferedImage.getWidth(), bufferedImage.getHeight(), 4, null);
        final BufferedImage texImage = new BufferedImage(glAlphaColorModel, raster, true, new Hashtable<>());

        final java.awt.Graphics g = texImage.getGraphics();
        g.drawImage(bufferedImage, 0, 0, null);

        final byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

        final ByteBuffer imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }


    private static void createWindow(final boolean fullscreen) throws Exception {
        final Application application = Application.getApplication();
        if (application != null) {
            final Image image = Toolkit.getDefaultToolkit().getImage(Graphics.class.getResource("/icons/boggarton128.png"));
            application.setDockIconImage(image);
        }

        if (!fullscreen) {
            Display.setDisplayMode(new DisplayMode(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT));
            Display.setLocation(300, 300);
            Display.setTitle("Boggarton");

            final ByteBuffer[] list = new ByteBuffer[3];
            list[0] = convertImageData(ImageIO.read(Objects.requireNonNull(Graphics.class.getResource("/icons/boggarton128.png"))));
            list[1] = convertImageData(ImageIO.read(Objects.requireNonNull(Graphics.class.getResource("/icons/boggarton32.png"))));
            list[2] = convertImageData(ImageIO.read(Objects.requireNonNull(Graphics.class.getResource("/icons/boggarton16.png"))));

            Display.setIcon(list);
        } else {
            Display.setFullscreen(true);
            try {
                final DisplayMode[] dm = org.lwjgl.util.Display.getAvailableDisplayModes(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT, -1, -1, -1, -1, 60, 100);
                org.lwjgl.util.Display.setDisplayMode(dm,
                        new String[] { "width=" + Const.SCREEN_WIDTH, "height=" + Const.SCREEN_HEIGHT, "freq=85", "bpp=" + Display.getDisplayMode().getBitsPerPixel() });
                actualDisplayMode = Display.getDesktopDisplayMode();
            } catch (final Exception e) {
                Sys.alert("Error", "Could not start full screen, switching to windowed mode");
                Display.setDisplayMode(new DisplayMode(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT));
            }
        }

        Display.create();
    }

    private static void initEngine() {
        try {
            Mouse.setGrabbed(false);
            createWindow(FULL_SCREEN);
            createOffScreenBuffer();
        } catch (final Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private static void initGL() {
        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glEnable(GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glLoadIdentity();
        if (FULL_SCREEN) {
            int actualWidth = actualDisplayMode.getWidth();
            int actualHeight = actualDisplayMode.getHeight();

            final double initialRatio = (double) SCREEN_WIDTH / SCREEN_HEIGHT;
            final double actualRatio = (double) actualWidth / actualHeight;
            final double ratioAmendment = actualRatio / initialRatio;

            actualWidth = (int) Math.round(SCREEN_WIDTH * ratioAmendment);
            actualHeight = SCREEN_HEIGHT;

            GLU.gluOrtho2D((float) -actualWidth / 2, (float) actualWidth / 2, (float) -actualHeight / 2, (float) actualHeight / 2);
        } else
            GLU.gluOrtho2D((float) -SCREEN_WIDTH / 2, (float) SCREEN_WIDTH / 2, (float) -SCREEN_HEIGHT / 2, (float) SCREEN_HEIGHT / 2);
        GL11.glMatrixMode(GL_MODELVIEW);
        TEXTURE_LOADER.init();
    }
}
