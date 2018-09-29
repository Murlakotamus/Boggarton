package com.foxcatgames.boggarton;

import static com.foxcatgames.boggarton.Const.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import com.foxcatgames.boggarton.scenes.SceneItem;
import com.foxcatgames.boggarton.scenes.types.SoundTypes;

public class Sound {
    static IntBuffer buffer = BufferUtils.createIntBuffer(10); // must be equals to number of sound files

    /** Sources are points emitting sound. */
    private static IntBuffer source = BufferUtils.createIntBuffer(53);

    /** Position of the source sound. */
    private static FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
    private static FloatBuffer sourcePosLeft = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { -1.0f, 0.0f, 0.0f }).rewind();
    private static FloatBuffer sourcePosRight = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 1.0f, 0.0f, 0.0f }).rewind();

    /** Velocity of the source sound. */
    private static FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /** Position of the listener. */
    private static FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /** Velocity of the listener. */
    private static FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /**
     * Orientation of the listener. (first 3 elements are "at", second 3 are "up")
     */
    private static FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f }).rewind();

    private static void loadPattern(final String filename, final int id) {
        WaveData waveFile = WaveData.create(Sound.class.getClass().getResource(filename));
        AL10.alBufferData(buffer.get(id), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
    }

    /**
     * We already defined certain values for the Listener, but we need to tell
     * OpenAL to use that data. This function does just that.
     */
    private static void setListenerValues() {
        AL10.alListener(AL10.AL_POSITION, listenerPos);
        AL10.alListener(AL10.AL_VELOCITY, listenerVel);
        AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
    }

    private static void initPatternCommon(final int bufferId, final int sourceId) {
        AL10.alSourcei(source.get(sourceId), AL10.AL_BUFFER, buffer.get(bufferId));
        AL10.alSourcef(source.get(sourceId), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(sourceId), AL10.AL_GAIN, 1.0f);
        AL10.alSource(source.get(sourceId), AL10.AL_VELOCITY, sourceVel);
    }

    private static void initPattern(final int bufferId, final int sourceId) {
        initPatternCommon(bufferId, sourceId);
        AL10.alSource(source.get(sourceId), AL10.AL_POSITION, sourcePos);
    }

    private static void initPatternLeft(final int bufferId, final int sourceId) {
        initPatternCommon(bufferId, sourceId);
        AL10.alSource(source.get(sourceId), AL10.AL_POSITION, sourcePosLeft);
    }

    private static void initPatternRight(final int bufferId, final int sourceId) {
        initPatternCommon(bufferId, sourceId);
        AL10.alSource(source.get(sourceId), AL10.AL_POSITION, sourcePosRight);
    }

    private static int loadALData() {
        // Load wav data into a buffer.
        AL10.alGenBuffers(buffer);

        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        loadPattern(WAV_ADDYUCK, SND_ADDYUCK);
        loadPattern(WAV_CYCLE, SND_CYCLE);
        loadPattern(WAV_DISAPPEAR, SND_DISAPPEAR);
        loadPattern(WAV_DROP, SND_DROP);
        loadPattern(WAV_MOVE, SND_MOVE);
        loadPattern(WAV_NEW, SND_NEW);
        loadPattern(WAV_SCORE, SND_SCORE);
        loadPattern(WAV_SELECT, SND_SELECT);
        loadPattern(WAV_SHIFT, SND_SHIFT);
        loadPattern(WAV_YUCK, SND_YUCK);

        // Bind the buffer with the source.
        AL10.alGenSources(source);
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

        // Do another error check and return.
        if (AL10.alGetError() == AL10.AL_NO_ERROR)
            return AL10.AL_TRUE;

        return AL10.AL_FALSE;
    }

    /**
     * We have allocated memory for our buffers and sources which needs to be
     * returned to the system. This function frees that memory.
     */
    private static void killALData() {
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
    }

    public static void init() {
        try {
            AL.create();
        } catch (LWJGLException le) {
            le.printStackTrace();
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
        else
            Logger.err("No source! " + currentSound);
    }

    public static void playMove() {
        play(SND_MOVE);
    }

    public static void playSelect() {
        play(SND_SELECT);
    }

    public static void play(final int sound) {
        if (SceneItem.getSound() == SoundTypes.ON)
            AL10.alSourcePlay(source.get(sound));
    }

    public static boolean isBusy(final int sound) {
        return AL10.alGetSourcei(source.get(sound), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }
}
