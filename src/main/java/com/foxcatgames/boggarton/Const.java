package com.foxcatgames.boggarton;

import java.util.HashMap;
import java.util.Map;

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

    // game log
    public static final String FIGURE_STR = "FIGURE: ";
    public static final String YUCK_STR = "YUCK:   ";
    public static final String GAMEOVER_STR = "Game over!";

    // sounds ids
    public static final int SND_MOVE = 0;
    public static final int SND_SELECT = 1;

    public static final int SND_ADDYUCK = 2;
    public static final int SND_ADDYUCK_LEFT = 3;
    public static final int SND_ADDYUCK_RIGHT = 4;

    public static final int SND_CRASH = 5;
    public static final int SND_CRASH_LEFT = 6;
    public static final int SND_CRASH_RIGHT = 7;

    public static final int SND_CYCLE = 8;
    public static final int SND_CYCLE_LEFT = 9;
    public static final int SND_CYCLE_RIGHT = 10;

    public static final int SND_NEW = 11;
    public static final int SND_NEW_LEFT = 12;
    public static final int SND_NEW_RIGHT = 13;

    public static final int SND_SCORE = 14;
    public static final int SND_SCORE_LEFT = 15;
    public static final int SND_SCORE_RIGHT = 16;

    public static final int SND_SHIFT = 17;
    public static final int SND_SHIFT_LEFT = 18;
    public static final int SND_SHIFT_RIGHT = 19;

    public static final int SND_YUCK = 20;
    public static final int SND_YUCK_LEFT = 21;
    public static final int SND_YUCK_RIGHT = 22;

    public static final int SND_DISAPPEAR = 23;
    public static final int SND_DISAPPEAR_LEFT = 24;
    public static final int SND_DISAPPEAR_RIGHT = 25;

    public static final int SND_DROP = 26;
    public static final int SND_DROP_LEFT = 27;
    public static final int SND_DROP_RIGHT = 28;

    // sound sources
    public static final int SND_DROP0 = 26;
    public static final int SND_DROP1 = 27;
    public static final int SND_DROP2 = 28;
    public static final int SND_DROP3 = 29;
    public static final int SND_DROP4 = 30;
    public static final int SND_DROP5 = 31;
    public static final int SND_DROP6 = 32;
    public static final int SND_DROP7 = 33;
    public static final int SND_DROP8 = 34;
    public static final int SND_DROP9 = 35;

    public static final int SND_DROP_LEFT0 = 36;
    public static final int SND_DROP_LEFT1 = 37;
    public static final int SND_DROP_LEFT2 = 38;
    public static final int SND_DROP_LEFT3 = 39;
    public static final int SND_DROP_LEFT4 = 40;
    public static final int SND_DROP_LEFT5 = 41;
    public static final int SND_DROP_LEFT6 = 42;
    public static final int SND_DROP_LEFT7 = 43;
    public static final int SND_DROP_LEFT8 = 44;
    public static final int SND_DROP_LEFT9 = 45;

    public static final int SND_DROP_RIGHT0 = 46;
    public static final int SND_DROP_RIGHT1 = 47;
    public static final int SND_DROP_RIGHT2 = 48;
    public static final int SND_DROP_RIGHT3 = 49;
    public static final int SND_DROP_RIGHT4 = 50;
    public static final int SND_DROP_RIGHT5 = 51;
    public static final int SND_DROP_RIGHT6 = 52;
    public static final int SND_DROP_RIGHT7 = 53;
    public static final int SND_DROP_RIGHT8 = 54;
    public static final int SND_DROP_RIGHT9 = 55;

    // sound files
    private static final String WAV_PATH = "/sounds/";
    private static final String LEFT = "_left";
    private static final String RIGHT = "_right";
    private static final String WAV = ".wav";

    public static final String ADDYUCK = "addyuck";
    public static final String WAV_ADDYUCK = WAV_PATH + ADDYUCK + WAV;
    public static final String WAV_ADDYUCK_LEFT = WAV_PATH + ADDYUCK + LEFT + WAV;
    public static final String WAV_ADDYUCK_RIGHT = WAV_PATH + ADDYUCK + RIGHT + WAV;

    public static final String CRASH = "crash";
    public static final String WAV_CRASH = WAV_PATH + CRASH + WAV;
    public static final String WAV_CRASH_LEFT = WAV_PATH + CRASH + LEFT + WAV;
    public static final String WAV_CRASH_RIGHT = WAV_PATH + CRASH + RIGHT + WAV;

    public static final String CYCLE = "cycle";
    public static final String WAV_CYCLE = WAV_PATH + CYCLE + WAV;
    public static final String WAV_CYCLE_LEFT = WAV_PATH + CYCLE + LEFT + WAV;
    public static final String WAV_CYCLE_RIGHT = WAV_PATH + CYCLE + RIGHT + WAV;

    public static final String DISAPPEAR = "disappear";
    public static final String WAV_DISAPPEAR = WAV_PATH + DISAPPEAR + WAV;
    public static final String WAV_DISAPPEAR_LEFT = WAV_PATH + DISAPPEAR + LEFT + WAV;
    public static final String WAV_DISAPPEAR_RIGHT = WAV_PATH + DISAPPEAR + RIGHT + WAV;

    public static final String DROP = "drop";
    public static final String WAV_DROP = WAV_PATH + DROP + WAV;
    public static final String WAV_DROP_LEFT = WAV_PATH + DROP + LEFT + WAV;
    public static final String WAV_DROP_RIGHT = WAV_PATH + DROP + RIGHT + WAV;

    public static final String WAV_MOVE = WAV_PATH + "move" + WAV;

    public static final String NEW = "new";
    public static final String WAV_NEW = WAV_PATH + NEW + WAV;
    public static final String WAV_NEW_LEFT = WAV_PATH + NEW + LEFT + WAV;
    public static final String WAV_NEW_RIGHT = WAV_PATH + NEW + RIGHT + WAV;

    public static final String SCORE = "score";
    public static final String WAV_SCORE = WAV_PATH + SCORE + WAV;
    public static final String WAV_SCORE_LEFT = WAV_PATH + SCORE + LEFT + WAV;
    public static final String WAV_SCORE_RIGHT = WAV_PATH + SCORE + RIGHT + WAV;

    public static final String WAV_SELECT = WAV_PATH + "select" + WAV;

    public static final String SHIFT = "shift";
    public static final String WAV_SHIFT = WAV_PATH + SHIFT + WAV;
    public static final String WAV_SHIFT_LEFT = WAV_PATH + SHIFT + LEFT + WAV;
    public static final String WAV_SHIFT_RIGHT = WAV_PATH + SHIFT + RIGHT + WAV;

    public static final String YUCK = "yuck";
    public static final String WAV_YUCK = WAV_PATH + YUCK + WAV;
    public static final String WAV_YUCK_LEFT = WAV_PATH + YUCK + LEFT + WAV;
    public static final String WAV_YUCK_RIGHT = WAV_PATH + YUCK + RIGHT + WAV;

    public static final Map<String, Integer> SOUNDS = new HashMap<>();
    public static final Map<String, Integer> SOUNDS_LEFT = new HashMap<>();
    public static final Map<String, Integer> SOUNDS_RIGHT = new HashMap<>();

    static {
        SOUNDS.put(ADDYUCK, SND_ADDYUCK);
        SOUNDS.put(CRASH, SND_CRASH);
        SOUNDS.put(CYCLE, SND_CYCLE);
        SOUNDS.put(DISAPPEAR, SND_DISAPPEAR);
        SOUNDS.put(DROP, SND_DROP0);
        SOUNDS.put(NEW, SND_NEW);
        SOUNDS.put(SCORE, SND_SCORE);
        SOUNDS.put(SHIFT, SND_SHIFT);
        SOUNDS.put(YUCK, SND_YUCK);

        SOUNDS_LEFT.put(ADDYUCK, SND_ADDYUCK_LEFT);
        SOUNDS_LEFT.put(CRASH, SND_CRASH_LEFT);
        SOUNDS_LEFT.put(CYCLE, SND_CYCLE_LEFT);
        SOUNDS_LEFT.put(DISAPPEAR, SND_DISAPPEAR_LEFT);
        SOUNDS_LEFT.put(DROP, SND_DROP_LEFT0);
        SOUNDS_LEFT.put(NEW, SND_NEW_LEFT);
        SOUNDS_LEFT.put(SCORE, SND_SCORE_LEFT);
        SOUNDS_LEFT.put(SHIFT, SND_SHIFT_LEFT);
        SOUNDS_LEFT.put(YUCK, SND_YUCK_LEFT);

        SOUNDS_RIGHT.put(ADDYUCK, SND_ADDYUCK_RIGHT);
        SOUNDS_RIGHT.put(CRASH, SND_CRASH_RIGHT);
        SOUNDS_RIGHT.put(CYCLE, SND_CYCLE_RIGHT);
        SOUNDS_RIGHT.put(DISAPPEAR, SND_DISAPPEAR_RIGHT);
        SOUNDS_RIGHT.put(DROP, SND_DROP_RIGHT0);
        SOUNDS_RIGHT.put(NEW, SND_NEW_RIGHT);
        SOUNDS_RIGHT.put(SCORE, SND_SCORE_RIGHT);
        SOUNDS_RIGHT.put(SHIFT, SND_SHIFT_RIGHT);
        SOUNDS_RIGHT.put(YUCK, SND_YUCK_RIGHT);
    }
}
