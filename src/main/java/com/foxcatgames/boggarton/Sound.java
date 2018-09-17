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
    static IntBuffer buffer = BufferUtils.createIntBuffer(26); // must be equals to number of sound files

    /** Sources are points emitting sound. */
    private static IntBuffer source = BufferUtils.createIntBuffer(53);

    /** Position of the source sound. */
    private static FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

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

    private static void initPattern(final int bufferId, final int sourceId) {
        AL10.alSourcei(source.get(sourceId), AL10.AL_BUFFER, buffer.get(bufferId));
        AL10.alSourcef(source.get(sourceId), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(sourceId), AL10.AL_GAIN, 1.0f);
        AL10.alSource(source.get(sourceId), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(sourceId), AL10.AL_VELOCITY, sourceVel);
    }

    private static int loadALData() {
        // Load wav data into a buffer.
        AL10.alGenBuffers(buffer);

        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        loadPattern(WAV_ADDYUCK, SND_ADDYUCK);
        loadPattern(WAV_ADDYUCK_LEFT, SND_ADDYUCK_LEFT);
        loadPattern(WAV_ADDYUCK_RIGHT, SND_ADDYUCK_RIGHT);

        loadPattern(WAV_CYCLE, SND_CYCLE);
        loadPattern(WAV_CYCLE_LEFT, SND_CYCLE_LEFT);
        loadPattern(WAV_CYCLE_RIGHT, SND_CYCLE_RIGHT);

        loadPattern(WAV_DISAPPEAR, SND_DISAPPEAR);
        loadPattern(WAV_DISAPPEAR_LEFT, SND_DISAPPEAR_LEFT);
        loadPattern(WAV_DISAPPEAR_RIGHT, SND_DISAPPEAR_RIGHT);

        loadPattern(WAV_DROP, SND_DROP);
        loadPattern(WAV_DROP_LEFT, SND_DROP_LEFT);
        loadPattern(WAV_DROP_RIGHT, SND_DROP_RIGHT);

        loadPattern(WAV_MOVE, SND_MOVE);

        loadPattern(WAV_NEW, SND_NEW);
        loadPattern(WAV_NEW_LEFT, SND_NEW_LEFT);
        loadPattern(WAV_NEW_RIGHT, SND_NEW_RIGHT);

        loadPattern(WAV_SCORE, SND_SCORE);
        loadPattern(WAV_SCORE_LEFT, SND_SCORE_LEFT);
        loadPattern(WAV_SCORE_RIGHT, SND_SCORE_RIGHT);

        loadPattern(WAV_SELECT, SND_SELECT);

        loadPattern(WAV_SHIFT, SND_SHIFT);
        loadPattern(WAV_SHIFT_LEFT, SND_SHIFT_LEFT);
        loadPattern(WAV_SHIFT_RIGHT, SND_SHIFT_RIGHT);

        loadPattern(WAV_YUCK, SND_YUCK);
        loadPattern(WAV_YUCK_LEFT, SND_YUCK_LEFT);
        loadPattern(WAV_YUCK_RIGHT, SND_YUCK_RIGHT);

        // Bind the buffer with the source.
        AL10.alGenSources(source);
        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        initPattern(SND_ADDYUCK, SND_ADDYUCK);
        initPattern(SND_ADDYUCK_LEFT, SND_ADDYUCK_LEFT);
        initPattern(SND_ADDYUCK_RIGHT, SND_ADDYUCK_RIGHT);

        initPattern(SND_CYCLE, SND_CYCLE);
        initPattern(SND_CYCLE_LEFT, SND_CYCLE_LEFT);
        initPattern(SND_CYCLE_RIGHT, SND_CYCLE_RIGHT);

        initPattern(SND_DISAPPEAR, SND_DISAPPEAR);
        initPattern(SND_DISAPPEAR_LEFT, SND_DISAPPEAR_LEFT);
        initPattern(SND_DISAPPEAR_RIGHT, SND_DISAPPEAR_RIGHT);

        initPattern(SND_DROP, SND_DROP0);
        initPattern(SND_DROP, SND_DROP1);
        initPattern(SND_DROP, SND_DROP2);
        initPattern(SND_DROP, SND_DROP3);
        initPattern(SND_DROP, SND_DROP4);
        initPattern(SND_DROP, SND_DROP5);
        initPattern(SND_DROP, SND_DROP6);
        initPattern(SND_DROP, SND_DROP7);
        initPattern(SND_DROP, SND_DROP8);
        initPattern(SND_DROP, SND_DROP9);

        initPattern(SND_DROP_LEFT, SND_DROP_LEFT0);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT1);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT2);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT3);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT4);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT5);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT6);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT7);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT8);
        initPattern(SND_DROP_LEFT, SND_DROP_LEFT9);

        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT0);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT1);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT2);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT3);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT4);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT5);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT6);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT7);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT8);
        initPattern(SND_DROP_RIGHT, SND_DROP_RIGHT9);

        initPattern(SND_MOVE, SND_MOVE);

        initPattern(SND_NEW, SND_NEW);
        initPattern(SND_NEW_LEFT, SND_NEW_LEFT);
        initPattern(SND_NEW_RIGHT, SND_NEW_RIGHT);

        initPattern(SND_SCORE, SND_SCORE);
        initPattern(SND_SCORE_LEFT, SND_SCORE_LEFT);
        initPattern(SND_SCORE_RIGHT, SND_SCORE_RIGHT);

        initPattern(SND_SELECT, SND_SELECT);

        initPattern(SND_SHIFT, SND_SHIFT);
        initPattern(SND_SHIFT_LEFT, SND_SHIFT_LEFT);
        initPattern(SND_SHIFT_RIGHT, SND_SHIFT_RIGHT);

        initPattern(SND_YUCK, SND_YUCK);
        initPattern(SND_YUCK_LEFT, SND_YUCK_LEFT);
        initPattern(SND_YUCK_RIGHT, SND_YUCK_RIGHT);

        // Do another error check and return.
        if (AL10.alGetError() == AL10.AL_NO_ERROR)
            return AL10.AL_TRUE;

        return AL10.AL_FALSE;
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
