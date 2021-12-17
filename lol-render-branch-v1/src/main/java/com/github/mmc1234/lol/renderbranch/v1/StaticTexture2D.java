package com.github.mmc1234.lol.renderbranch.v1;

import jdk.incubator.foreign.*;
import org.lwjgl.opengl.*;

public class StaticTexture2D extends AbstractTexture {
    public StaticTexture2D(TextureFilterMode majorFilter, TextureFilterMode minorFilter, TextureFormat format, TextureFormat internalFormat, int width, int height) {
        super(majorFilter, minorFilter, format, internalFormat, width, height);
    }

    private MemorySegment pixels;
    private int mipmapLevel;
    public void setPixelsHint(MemorySegment pixels) {
        this.pixels = pixels;
    }

    public void setMipmapLevelHint(int mipmapLevel) {
        this.mipmapLevel = Math.max(0, mipmapLevel);
    }

    public int asFormat(TextureFormat format) {
        // TODO
        return GL33.GL_RGBA;
    }

    private boolean isEnableMipmap() {
        return mipmapLevel > 0;
    }

    @Override
    public void init() {
        if(nativeTexture == 0 && pixels != null && pixels.byteSize() > 0) {
            nativeTexture = GL33.glGenTextures();
            GL33.glActiveTexture(GL33.GL_TEXTURE0+0);
            GL33.glBindTexture(GL33.GL_TEXTURE_2D, nativeTexture);
            GL33.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            var internalFmt = asFormat(getInternalFormat());
            var fmt = asFormat(getFormat());
            int type = GL33.GL_UNSIGNED_BYTE; // TODO
            GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_TEXTURE_MIN_FILTER, filterModeAsInt(getMinFilter()));
            GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_TEXTURE_MAG_FILTER, filterModeAsInt(getMagFilter()));
            if(isEnableMipmap()) {
                GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_GENERATE_MIPMAP, GL33.GL_TRUE);
                GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_TEXTURE_MAX_LEVEL, mipmapLevel);
            }
            GL33.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFmt, getWidth(), getHeight(), 0, fmt, type, pixels.address().toRawLongValue());
            pixels = null;
        }
    }

    @Override
    public void close() {
        if(pixels != null) {
            pixels = null;
        }
        if(nativeTexture != 0) {
            GL33.glDeleteTextures(nativeTexture);
        }
    }
}
