package com.foxcatgames.boggarton;

import java.util.HashMap;
import java.util.Map;

import com.foxcatgames.boggarton.engine.TextureLoader;
import com.foxcatgames.boggarton.players.virtual.solver.FullnessEater;
import com.foxcatgames.boggarton.players.virtual.solver.IEater;
import com.foxcatgames.boggarton.players.virtual.solver.ReactionsEater;

public class Const {

    public static final String CONFIG = "config.ini";

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

    public static final char LEFT = '←';
    public static final char RIGHT = '→';
    public static final char UP = '↑';
    public static final char DOWN = '↓';
    public static final char NEXT = '↵';

    public static final String DOWN_ = "" + DOWN;
    public static final String DOWN_NEXT = "" + DOWN + NEXT;

    public static final float DEFAULT_Z = 0;
    public static final int CURRENT_SET = 1;

    // Init parameters
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;

    // Texture loader is used to load and server textures to entities on init
    public static final TextureLoader TEXTURE_LOADER = new TextureLoader();

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
    public static final String SCORE_STR = "SCORE: ";
    public static final String MOVES_STR = "MOVES: ";
    public static final String YUCK_STR = "YUCK: ";
    public static final String GAMEOVER_STR = "Game over!";

    public static final IEater FULLNESS_EATER = new FullnessEater();
    public static final IEater REACTIONS_EATER = new ReactionsEater();

    // sounds ids
    public static final int SND_ADDYUCK = 0;
    public static final int SND_CYCLE = 1;
    public static final int SND_DISAPPEAR = 2;
    public static final int SND_MOVE = 3;
    public static final int SND_NEW = 4;
    public static final int SND_SCORE = 5;
    public static final int SND_SELECT = 6;
    public static final int SND_SHIFT = 7;
    public static final int SND_YUCK = 8;
    public static final int SND_DROP = 9;

    // sound sources
    public static final int SND_DROP0 = 9;
    public static final int SND_DROP_LEFT0 = 19;
    public static final int SND_DROP_RIGHT0 = 29;

    public static final int SND_ADDYUCK_LEFT = 39;
    public static final int SND_ADDYUCK_RIGHT = 40;

    public static final int SND_CYCLE_LEFT = 41;
    public static final int SND_CYCLE_RIGHT = 42;

    public static final int SND_DISAPPEAR_LEFT = 43;
    public static final int SND_DISAPPEAR_RIGHT = 44;

    public static final int SND_NEW_LEFT = 45;
    public static final int SND_NEW_RIGHT = 46;

    public static final int SND_SCORE_LEFT = 47;
    public static final int SND_SCORE_RIGHT = 48;

    public static final int SND_SHIFT_LEFT = 49;
    public static final int SND_SHIFT_RIGHT = 50;

    public static final int SND_YUCK_LEFT = 51;
    public static final int SND_YUCK_RIGHT = 52;

    // sound files
    private static final String WAV_PATH = "/sounds/";
    private static final String WAV = ".wav";

    public static final String ADDYUCK = "addyuck";
    public static final String WAV_ADDYUCK = WAV_PATH + ADDYUCK + WAV;

    public static final String CYCLE = "cycle";
    public static final String WAV_CYCLE = WAV_PATH + CYCLE + WAV;

    public static final String DISAPPEAR = "disappear";
    public static final String WAV_DISAPPEAR = WAV_PATH + DISAPPEAR + WAV;

    public static final String DROP = "drop";
    public static final String WAV_DROP = WAV_PATH + DROP + WAV;

    public static final String WAV_MOVE = WAV_PATH + "move" + WAV;

    public static final String NEW = "new";
    public static final String WAV_NEW = WAV_PATH + NEW + WAV;

    public static final String SCORE = "score";
    public static final String WAV_SCORE = WAV_PATH + SCORE + WAV;

    public static final String WAV_SELECT = WAV_PATH + "select" + WAV;

    public static final String SHIFT = "shift";
    public static final String WAV_SHIFT = WAV_PATH + SHIFT + WAV;

    public static final String YUCK = "yuck";
    public static final String WAV_YUCK = WAV_PATH + YUCK + WAV;

    public static final Map<String, Integer> SOUNDS = new HashMap<>();
    public static final Map<String, Integer> SOUNDS_LEFT = new HashMap<>();
    public static final Map<String, Integer> SOUNDS_RIGHT = new HashMap<>();

    static {
        SOUNDS.put(ADDYUCK, SND_ADDYUCK);
        SOUNDS.put(CYCLE, SND_CYCLE);
        SOUNDS.put(DISAPPEAR, SND_DISAPPEAR);
        SOUNDS.put(DROP, SND_DROP0);
        SOUNDS.put(NEW, SND_NEW);
        SOUNDS.put(SCORE, SND_SCORE);
        SOUNDS.put(SHIFT, SND_SHIFT);
        SOUNDS.put(YUCK, SND_YUCK);

        SOUNDS_LEFT.put(ADDYUCK, SND_ADDYUCK_LEFT);
        SOUNDS_LEFT.put(CYCLE, SND_CYCLE_LEFT);
        SOUNDS_LEFT.put(DISAPPEAR, SND_DISAPPEAR_LEFT);
        SOUNDS_LEFT.put(DROP, SND_DROP_LEFT0);
        SOUNDS_LEFT.put(NEW, SND_NEW_LEFT);
        SOUNDS_LEFT.put(SCORE, SND_SCORE_LEFT);
        SOUNDS_LEFT.put(SHIFT, SND_SHIFT_LEFT);
        SOUNDS_LEFT.put(YUCK, SND_YUCK_LEFT);

        SOUNDS_RIGHT.put(ADDYUCK, SND_ADDYUCK_RIGHT);
        SOUNDS_RIGHT.put(CYCLE, SND_CYCLE_RIGHT);
        SOUNDS_RIGHT.put(DISAPPEAR, SND_DISAPPEAR_RIGHT);
        SOUNDS_RIGHT.put(DROP, SND_DROP_RIGHT0);
        SOUNDS_RIGHT.put(NEW, SND_NEW_RIGHT);
        SOUNDS_RIGHT.put(SCORE, SND_SCORE_RIGHT);
        SOUNDS_RIGHT.put(SHIFT, SND_SHIFT_RIGHT);
        SOUNDS_RIGHT.put(YUCK, SND_YUCK_RIGHT);
    }
}
