package com.foxcatgames.boggarton;

import com.foxcatgames.boggarton.engine.ITextureLoader;
import com.foxcatgames.boggarton.engine.TextureLoader;

public class Const {

    public static final int BOX = 30;
    public static final int BORDER = 5;

    static public final int MIN_DIFFICULTY = 4;
    static public final int MAX_DIFFICULTY = 7;
    static public final int MIN_SIZE = 3;
    static public final int MAX_SIZE = 5;
    static public final int MIN_PROGNOSIS = 1;
    static public final int MAX_PROGNOSIS = 3;

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

    public static final int TITLE_HEIGHT = 150;
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
    public static final int EMPTY = 18;

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

    public static final String FIGURE = "FIGURE: ";
    public static final String YUCK = "YUCK:   ";
    public static final String GAMEOVER = "Game over!";

    public static final int SND_MOVE = 0;
    public static final int SND_SELECT = 1;

    public static final int SND_DROP = 2;
    public static final int SND_DROP_LEFT = 3;
    public static final int SND_DROP_RIGHT = 4;

    public static final int SND_DROP0 = 2;
    public static final int SND_DROP1 = 3;
    public static final int SND_DROP2 = 4;
    public static final int SND_DROP3 = 5;
    public static final int SND_DROP4 = 6;

    public static final int SND_DROP_LEFT0 = 7;
    public static final int SND_DROP_LEFT1 = 8;
    public static final int SND_DROP_LEFT2 = 9;
    public static final int SND_DROP_LEFT3 = 10;
    public static final int SND_DROP_LEFT4 = 11;

    public static final int SND_DROP_RIGHT0 = 12;
    public static final int SND_DROP_RIGHT1 = 13;
    public static final int SND_DROP_RIGHT2 = 14;
    public static final int SND_DROP_RIGHT3 = 15;
    public static final int SND_DROP_RIGHT4 = 16;

    private static final String WAV_PATH = "/sounds/";
    private static final String WAV_EXT = ".wav";

    public static final String WAV_MOVE = WAV_PATH + "move" + WAV_EXT;
    public static final String WAV_SELECT = WAV_PATH + "select" + WAV_EXT;
    public static final String WAV_DROP = WAV_PATH + "drop" + WAV_EXT;
    public static final String WAV_DROP_LEFT = WAV_PATH + "drop_left" + WAV_EXT;
    public static final String WAV_DROP_RIGHT = WAV_PATH + "drop_right" + WAV_EXT;
}
