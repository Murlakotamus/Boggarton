package com.foxcatgames.boggarton;

import static com.foxcatgames.boggarton.Const.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import com.foxcatgames.boggarton.scenes.AbstractScene;
import com.foxcatgames.boggarton.scenes.SceneItem;
import com.foxcatgames.boggarton.scenes.types.SoundTypes;

public class Sound {
    public static final IntBuffer BUFFER = BufferUtils.createIntBuffer(16); // must be equals to number of sound files

    private static final IntBuffer SOURCE = BufferUtils.createIntBuffer(63); // real sound buffers - common ones and by channel

    private static final FloatBuffer SOURCE_POS = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
    private static final FloatBuffer SOURCE_LEFT = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { -1.0f, 0.0f, 0.0f }).rewind();
    private static final FloatBuffer SOURCE_RIGHT = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 1.0f, 0.0f, 0.0f }).rewind();

    private static final FloatBuffer SOURCE_VEL = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    private static final FloatBuffer LISTENER_POS = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
    private static final FloatBuffer LISTENER_VEL = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
    private static final FloatBuffer LISTENER_ORI = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f })
            .rewind();

    private static final Map<Integer, Float> PLAYING_SOUNDS = new ConcurrentHashMap<>();

    private static final float MOVE_DURATION = 0.3f;

    private static void setListenerValues() {
        AL10.alListener(AL10.AL_POSITION, LISTENER_POS);
        AL10.alListener(AL10.AL_VELOCITY, LISTENER_VEL);
        AL10.alListener(AL10.AL_ORIENTATION, LISTENER_ORI);
    }

    private static void initPatternCommon(final int bufferId, final int sourceId) {
        AL10.alSourcei(SOURCE.get(sourceId), AL10.AL_BUFFER, BUFFER.get(bufferId));
        AL10.alSourcef(SOURCE.get(sourceId), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(SOURCE.get(sourceId), AL10.AL_GAIN, 1.0f);
        AL10.alSource(SOURCE.get(sourceId), AL10.AL_VELOCITY, SOURCE_VEL);
    }

    private static void initPattern(final int bufferId, final int sourceId) {
        initPatternCommon(bufferId, sourceId);
        AL10.alSource(SOURCE.get(sourceId), AL10.AL_POSITION, SOURCE_POS);
    }

    private static void initPatternLeft(final int bufferId, final int sourceId) {
        initPatternCommon(bufferId, sourceId);
        AL10.alSource(SOURCE.get(sourceId), AL10.AL_POSITION, SOURCE_LEFT);
    }

    private static void initPatternRight(final int bufferId, final int sourceId) {
        initPatternCommon(bufferId, sourceId);
        AL10.alSource(SOURCE.get(sourceId), AL10.AL_POSITION, SOURCE_RIGHT);
    }

    private static int loadALData() {
        AL10.alGenBuffers(BUFFER);

        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        SoundLoader sl = new SoundLoader();
        sl.loadPattern(WAV_ADDYUCK, SND_ADDYUCK);
        sl.loadPattern(WAV_CYCLE, SND_CYCLE);
        sl.loadPattern(WAV_DISAPPEAR, SND_DISAPPEAR);
        sl.loadPattern(WAV_DROP, SND_DROP);
        sl.loadPattern(WAV_MOVE, SND_MOVE);
        sl.loadPattern(WAV_NEW, SND_NEW);
        sl.loadPattern(WAV_SCORE, SND_SCORE);
        sl.loadPattern(WAV_SELECT, SND_SELECT);
        sl.loadPattern(WAV_SHIFT, SND_SHIFT);
        sl.loadPattern(WAV_YUCK, SND_YUCK);

        sl.loadPattern(JINGLE_LOGO, SND_LOGO_ID);
        sl.loadPattern(JINGLE_WIN, SND_WIN_ID);
        sl.loadPattern(JINGLE_LOSE, SND_LOSE_ID);
        sl.loadPattern(JINGLE_NEUTRAL, SND_NEUTRAL_ID);

        sl.loadPattern(JINGLE_SUPER_SCORE, SND_SUPER_SCORE_ID);
        sl.loadPattern(JINGLE_MEGA_SCORE, SND_MEGA_SCORE_ID);

        AL10.alGenSources(SOURCE);
        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        initPattern(SND_ADDYUCK, SND_ADDYUCK);
        initPatternLeft(SND_ADDYUCK, SND_ADDYUCK_LEFT);
        initPatternRight(SND_ADDYUCK, SND_ADDYUCK_RIGHT);

        initPattern(SND_CYCLE, SND_CYCLE);
        initPatternLeft(SND_CYCLE, SND_CYCLE_LEFT);
        initPatternRight(SND_CYCLE, SND_CYCLE_RIGHT);

        initPattern(SND_DISAPPEAR, SND_DISAPPEAR);
        initPatternLeft(SND_DISAPPEAR, SND_DISAPPEAR_LEFT);
        initPatternRight(SND_DISAPPEAR, SND_DISAPPEAR_RIGHT);

        for (int i = SND_DROP0; i < SND_DROP0 + 10; i++)
            initPattern(SND_DROP, i);

        for (int i = SND_DROP_LEFT0; i < SND_DROP_LEFT0 + 10; i++)
            initPatternLeft(SND_DROP, i);

        for (int i = SND_DROP_RIGHT0; i < SND_DROP_RIGHT0 + 10; i++)
            initPatternRight(SND_DROP, i);

        initPattern(SND_MOVE, SND_MOVE);

        initPattern(SND_NEW, SND_NEW);
        initPatternLeft(SND_NEW, SND_NEW_LEFT);
        initPatternRight(SND_NEW, SND_NEW_RIGHT);

        initPattern(SND_SCORE, SND_SCORE);
        initPatternLeft(SND_SCORE, SND_SCORE_LEFT);
        initPatternRight(SND_SCORE, SND_SCORE_RIGHT);

        initPattern(SND_SELECT, SND_SELECT);

        initPattern(SND_SHIFT, SND_SHIFT);
        initPatternLeft(SND_SHIFT, SND_SHIFT_LEFT);
        initPatternRight(SND_SHIFT, SND_SHIFT_RIGHT);

        initPattern(SND_YUCK, SND_YUCK);
        initPatternLeft(SND_YUCK, SND_YUCK_LEFT);
        initPatternRight(SND_YUCK, SND_YUCK_RIGHT);

        initPattern(SND_LOGO_ID, SND_LOGO);
        initPattern(SND_WIN_ID, SND_WIN);
        initPattern(SND_LOSE_ID, SND_LOSE);
        initPattern(SND_NEUTRAL_ID, SND_NEUTRAL);

        initPattern(SND_SUPER_SCORE_ID, SND_SUPER_SCORE);
        initPatternLeft(SND_SUPER_SCORE_ID, SND_SUPER_SCORE_LEFT);
        initPatternRight(SND_SUPER_SCORE_ID, SND_SUPER_SCORE_RIGHT);

        initPattern(SND_MEGA_SCORE_ID, SND_MEGA_SCORE);
        initPatternLeft(SND_MEGA_SCORE_ID, SND_MEGA_SCORE_LEFT);
        initPatternRight(SND_MEGA_SCORE_ID, SND_MEGA_SCORE_RIGHT);

        if (AL10.alGetError() == AL10.AL_NO_ERROR)
            return AL10.AL_TRUE;

        return AL10.AL_FALSE;
    }

    private static void killALData() {
        AL10.alDeleteSources(SOURCE);
        AL10.alDeleteBuffers(BUFFER);
    }

    public static void init() {
        try {
            AL.create();
        } catch (LWJGLException e) {
            Logger.printStackTrace(e);
            return;
        }
        AL10.alGetError();

        if (loadALData() == AL10.AL_FALSE) {
            Logger.err("Error loading data.");
            return;
        }

        setListenerValues();
    }

    public static void destroy() {
        killALData();
        AL.destroy();
    }

    public static void playDrop(final int sound) {
        int currentSound = sound;
        while (currentSound < sound + 10 && isBusy(currentSound))
            currentSound++;

        if (currentSound < sound + 10)
            play(currentSound);
    }

    public static void playMove() {
        play(SND_MOVE);
    }

    public static void playSelect() {
        play(SND_SELECT);
    }

    public static void playLogo() {
        play(SND_LOGO);
    }

    public static void playWin() {
        play(SND_WIN);
    }

    public static void playLose() {
        play(SND_LOSE);
    }

    public static void playNeutral() {
        play(SND_NEUTRAL);
    }

    public static void play(final int sound) {
        PLAYING_SOUNDS.put(sound, AbstractScene.getTime());
        if (SceneItem.getSound() == SoundTypes.ON)
            AL10.alSourcePlay(SOURCE.get(sound));
    }

    public static boolean isPlaying(final int sound) {
        final float time = PLAYING_SOUNDS.getOrDefault(sound, 0f);
        return time + MOVE_DURATION > AbstractScene.getTime();
    }

    public static boolean isBusy(final int sound) {
        return AL10.alGetSourcei(SOURCE.get(sound), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }
}

class SoundLoader {

    public void loadPattern(final String filename, final int id) {
        WaveData waveFile = WaveData.create(this.getClass().getResource(filename));
        assert waveFile != null;
        AL10.alBufferData(Sound.BUFFER.get(id), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
    }
}
