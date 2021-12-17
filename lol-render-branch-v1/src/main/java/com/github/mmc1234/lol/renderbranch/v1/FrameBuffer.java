package com.github.mmc1234.lol.renderbranch.v1;

import org.lwjgl.opengl.*;

public class FrameBuffer {
    private int nativeFrameBuffer;
    private boolean isDefaultBuffer;

    public int getNativeFrameBuffer() {
        return nativeFrameBuffer;
    }

    public static final FrameBuffer newDefault() {
        return new FrameBuffer(true);
    }

    public static final FrameBuffer newNormal() {
        return new FrameBuffer(true);
    }

    private FrameBuffer(boolean isDefaultBuffer) {
        this.isDefaultBuffer = isDefaultBuffer;
    }

    public void init() {
        if(!isDefaultBuffer) {
            this.nativeFrameBuffer = GL33.glGenFramebuffers();
            // TODO
        }
    }

    public void close() {
        if(!isDefaultBuffer) {
            GL33.glDeleteFramebuffers(nativeFrameBuffer);
            nativeFrameBuffer = 0;
        }
    }
}
