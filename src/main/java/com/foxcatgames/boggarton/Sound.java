package com.foxcatgames.boggarton;

import static com.foxcatgames.boggarton.Const.SND_MOVE;
import static com.foxcatgames.boggarton.Const.SND_SELECT;
import static com.foxcatgames.boggarton.Const.WAV_MOVE;
import static com.foxcatgames.boggarton.Const.WAV_SELECT;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;


public class Sound {
    static IntBuffer buffer = BufferUtils.createIntBuffer(2);

    /** Sources are points emitting sound. */
    public static IntBuffer source = BufferUtils.createIntBuffer(2);

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

        WaveData waveFile = WaveData.create(Sound.class.getClass().getResource(WAV_MOVE));
        AL10.alBufferData(buffer.get(SND_MOVE), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();

        waveFile = WaveData.create(Sound.class.getClass().getResource(WAV_SELECT));
        AL10.alBufferData(buffer.get(SND_SELECT), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();

        // Bind the buffer with the source.
        AL10.alGenSources(source);

        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        AL10.alSourcei(source.get(SND_MOVE), AL10.AL_BUFFER, buffer.get(SND_MOVE));
        AL10.alSourcef(source.get(SND_MOVE), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(SND_MOVE), AL10.AL_GAIN, 1.0f);
        AL10.alSource(source.get(SND_MOVE), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(SND_MOVE), AL10.AL_VELOCITY, sourceVel);

        AL10.alSourcei(source.get(SND_SELECT), AL10.AL_BUFFER, buffer.get(SND_SELECT));
        AL10.alSourcef(source.get(SND_SELECT), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(SND_SELECT), AL10.AL_GAIN, 1.0f);
        AL10.alSource(source.get(SND_SELECT), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(SND_SELECT), AL10.AL_VELOCITY, sourceVel);
        
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
