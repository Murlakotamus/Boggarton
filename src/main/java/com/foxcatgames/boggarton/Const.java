package com.foxcatgames.boggarton;

import com.foxcatgames.boggarton.engine.ITextureLoader;
import com.foxcatgames.boggarton.engine.TextureLoader;

public class Const {

    public static final int BOX = 30;
    public static final int BORDER = 5;

    public static final int WIDTH = 6;
    public static final int HEIGHT = 12;

    public static final float DEFAULT_Z = 0;
    public static final int CURRENT_SET = 1;

    // Init parameters
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;

    // Texture loader is used to load and server textures to entities on init
    public static final ITextureLoader TEXTURE_LOADER = new TextureLoader();;

    public static final int FONT_WIDTH = 14;
    public static final int FONT_HEIGHT = 28;

    // decorations
    public static final int ABOUT = 1;
    public static final int GAME_OVER = 2;
    public static final int LOSER = 3;
    public static final int TITLE = 5;
    public static final int WINNER = 6;
    public static final int GAME_PAUSED = 9;

    // fonts
    public static final int DARK_FONT = 7;
    public static final int LIGHT_FONT = 8;

    // brick set
    public static final int A = 11;
    public static final int B = 12;
    public static final int C = 13;
    public static final int D = 14;
    public static final int E = 15;
    public static final int F = 16;
    public static final int G = 17;

    // frame elements
    public static final int FRAME_UPPER = 101;
    public static final int FRAME_RIGHT = 102;
    public static final int FRAME_LOWER = 103;
    public static final int FRAME_LEFT = 104;

    public static final int FRAME_UPPER_LEFT = 105;
    public static final int FRAME_UPPER_RIGHT = 106;

    public static final int FRAME_LOWER_LEFT = 107;
    public static final int FRAME_LOWER_RIGHT = 108;

    public static final int FRAME_FULL_RIGHT = 109;
    public static final int FRAME_FULL_LEFT = 110;
    public static final int FRAME_UPPER_BRIDGE = 111;
    public static final int FRAME_LOWER_BRIDGE = 112;
}