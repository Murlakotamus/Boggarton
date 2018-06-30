package com.foxcatgames.boggarton;

import static org.lwjgl.opengl.GL11.*;
import static com.foxcatgames.boggarton.Const.SCREEN_HEIGHT;
import static com.foxcatgames.boggarton.Const.SCREEN_WIDTH;
import static com.foxcatgames.boggarton.Const.TEXTURE_LOADER;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Graphics {

    private static final boolean FULL_SCREEN = false;
    private static DisplayMode actualDisplayMode = null;

    public static void init() {
        initEngine();
        initGL();
    }

    public static void destroy() {
        Display.destroy();
    }

    /**
     * Off-screen buffer
     */
    private static void createOffScreenBuffer() {
        final int bytesPerPixel = 3;
        final ByteBuffer scratch = ByteBuffer.allocateDirect(1024 * 1024 * bytesPerPixel);
        final IntBuffer buf = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL11.glGenTextures(buf); // Create Texture In OpenGL
        GL11.glBindTexture(GL_TEXTURE_2D, buf.get(0));

        final int glType = GL_RGB;
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, glType, 1024, 1024, 0, glType, GL_UNSIGNED_BYTE, scratch);
    }

    /**
     * create OpenGL window
     */
    private static void createWindow(int screenWidth, int screenHeight, boolean fullscreen) throws Exception {

        if (!fullscreen) { // create windowed mode
            Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
            Display.setLocation(300, 300);
        } else {
            Display.setFullscreen(true);
            try {
                final DisplayMode dm[] = org.lwjgl.util.Display.getAvailableDisplayModes(screenWidth, screenHeight, -1, -1, -1, -1, 60, 100);
                org.lwjgl.util.Display.setDisplayMode(dm,
                        new String[] { "width=" + screenWidth, "height=" + screenHeight, "freq=85", "bpp=" + Display.getDisplayMode().getBitsPerPixel() });
                actualDisplayMode = Display.getDesktopDisplayMode();
            } catch (Exception e) {
                Sys.alert("Error", "Could not start full screen, switching to windowed mode");
                Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
            }
        }

        Display.create();
    }

    private static void initEngine() {
        try {
            Mouse.setGrabbed(false);
            createWindow(SCREEN_WIDTH, SCREEN_HEIGHT, FULL_SCREEN);
            createOffScreenBuffer();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * OpenGL initialization
     */
    private static void initGL() {
        GL11.glEnable(GL_TEXTURE_2D); // Enable Texture Mapping
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glDisable(GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glEnable(GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix
        if (FULL_SCREEN) {
            int actualWidth = actualDisplayMode.getWidth();
            int actualHeight = actualDisplayMode.getHeight();

            final double initialRatio = (double) SCREEN_WIDTH / SCREEN_HEIGHT;
            final double actualRatio = (double) actualWidth / actualHeight;
            final double ratioAmendment = actualRatio / initialRatio;

            actualWidth = (int) Math.round(SCREEN_WIDTH * ratioAmendment);
            actualHeight = SCREEN_HEIGHT;

            GLU.gluOrtho2D(-(int) actualWidth / 2, (int) actualWidth / 2, -(int) actualHeight / 2, (int) actualHeight / 2);
        } else
            GLU.gluOrtho2D(-(int) SCREEN_WIDTH / 2, (int) SCREEN_WIDTH / 2, -(int) SCREEN_HEIGHT / 2, (int) SCREEN_HEIGHT / 2);
        GL11.glMatrixMode(GL_MODELVIEW);
        TEXTURE_LOADER.init();
    }
}
