package com.foxcatgames.boggarton;

import static com.foxcatgames.boggarton.Const.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound {
    static IntBuffer buffer = BufferUtils.createIntBuffer(17);

    /** Sources are points emitting sound. */
    public static IntBuffer source = BufferUtils.createIntBuffer(17);

    /** Position of the source sound. */
    static FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /** Velocity of the source sound. */
    static FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /** Position of the listener. */
    static FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /** Velocity of the listener. */
    static FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /**
     * Orientation of the listener. (first 3 elements are "at", second 3 are "up")
     */
    static FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f }).rewind();

    static void loadPattern(String filename, int id) {
        WaveData waveFile = WaveData.create(Sound.class.getClass().getResource(filename));
        AL10.alBufferData(buffer.get(id), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
    }

    private static void initPattern(int id) {
        AL10.alSourcei(source.get(id), AL10.AL_BUFFER, buffer.get(id));
        AL10.alSourcef(source.get(id), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(id), AL10.AL_GAIN, 1.0f);
        AL10.alSource(source.get(id), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(id), AL10.AL_VELOCITY, sourceVel);
    }

    private static void initAllPatterns(int... ids) {
        for (int id : ids)
            initPattern(id);
    }

    /**
     * boolean LoadALData()
     *
     * This function will load our sample data from the disk using the Alut utility
     * and send the data into OpenAL as a buffer. A source is then also created to
     * play that buffer.
     */
    static int loadALData() {
        // Load wav data into a buffer.
        AL10.alGenBuffers(buffer);

        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        loadPattern(WAV_MOVE, SND_MOVE);
        loadPattern(WAV_SELECT, SND_SELECT);

        loadPattern(WAV_DROP, SND_DROP0);
        loadPattern(WAV_DROP, SND_DROP1);
        loadPattern(WAV_DROP, SND_DROP2);
        loadPattern(WAV_DROP, SND_DROP3);
        loadPattern(WAV_DROP, SND_DROP4);

        loadPattern(WAV_DROP_LEFT, SND_DROP_LEFT0);
        loadPattern(WAV_DROP_LEFT, SND_DROP_LEFT1);
        loadPattern(WAV_DROP_LEFT, SND_DROP_LEFT2);
        loadPattern(WAV_DROP_LEFT, SND_DROP_LEFT3);
        loadPattern(WAV_DROP_LEFT, SND_DROP_LEFT4);

        loadPattern(WAV_DROP_RIGHT, SND_DROP_RIGHT0);
        loadPattern(WAV_DROP_RIGHT, SND_DROP_RIGHT1);
        loadPattern(WAV_DROP_RIGHT, SND_DROP_RIGHT2);
        loadPattern(WAV_DROP_RIGHT, SND_DROP_RIGHT3);
        loadPattern(WAV_DROP_RIGHT, SND_DROP_RIGHT4);

        // Bind the buffer with the source.
        AL10.alGenSources(source);
        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        initAllPatterns(SND_MOVE, SND_SELECT, SND_DROP0, SND_DROP_LEFT0, SND_DROP_RIGHT0, SND_DROP1, SND_DROP_LEFT1, SND_DROP_RIGHT1, SND_DROP2, SND_DROP_LEFT2,
                SND_DROP_RIGHT2, SND_DROP3, SND_DROP_LEFT3, SND_DROP_RIGHT3, SND_DROP4, SND_DROP_LEFT4, SND_DROP_RIGHT4);

        // Do another error check and return.
        if (AL10.alGetError() == AL10.AL_NO_ERROR)
            return AL10.AL_TRUE;

        return AL10.AL_FALSE;
    }

    /**
     * void setListenerValues()
     *
     * We already defined certain values for the Listener, but we need to tell
     * OpenAL to use that data. This function does just that.
     */
    static void setListenerValues() {
        AL10.alListener(AL10.AL_POSITION, listenerPos);
        AL10.alListener(AL10.AL_VELOCITY, listenerVel);
        AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
    }

    /**
     * void killALData()
     *
     * We have allocated memory for our buffers and sources which needs to be
     * returned to the system. This function frees that memory.
     */
    static void killALData() {
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
    }

    static public void init() {
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
        Sound.killALData();
        AL.destroy();
    }
}
